package com.app.sqds.hibernate;

import com.app.sqds.util.BeanUtils;
import com.app.sqds.util.GenericsUtils;
import com.app.sqds.util.PageInfo;
import com.app.sqds.util.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.Query;
import org.hibernate.transform.ResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * hibernate 数据库基类
 * @author ccj
 * @param <T>
 */
@Transactional
public class HibernateDao<T> {
    private final Logger logger = LoggerFactory.getLogger(HibernateDao.class);
    private Class<T> entityClass;

    private EntityManager entityManager;

    @Autowired
    public void setEntityManager(EntityManager entityManager){
        this.entityManager = entityManager;
        entityClass = GenericsUtils.getSuperClassGenricType(getClass());
    }

    public Session getSession() {
        if (entityManager.unwrap(Session.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        return entityManager.unwrap(Session.class);
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    /**
     * 保存
     *
     * @param entity
     */
    public void save(T entity) {
        if (entity != null) {
            getSession().saveOrUpdate(entity);
        }
    }

    public void merge(T entity){
        if(entity != null){
            getSession().merge(entity);
        }
    }

    /**
     * dynamic model 方式保存对象
     *
     * @param k
     * @param o
     */
    public void save(String k, Object o) {
        if (o != null) {
            getSession().saveOrUpdate(k, o);
        }
    }


    /**
     * 更新
     *
     * @param entity
     */
    public void update(T entity) {
        save(entity);
    }

    /**
     * 根据主键删除某记录
     *
     * @param id
     */
    public void delete(Serializable id) {
        if (id != null) {
            deleteObject(get(id));
        }
    }

    public void deleteObject(T entity) {
        if (entity != null) {
            getSession().delete(entity);
        }
    }

    /**
     * 删除记录，用于dynamic model
     *
     * @param name
     * @param id
     */
    public void delete(String name, Serializable id) {
        Object object = this.get(name, id);
        if (object != null) {
            getSession().delete(name, object);
        }
    }

    /**
     * 查找数据库所有记录
     *
     * @return
     */
    public List<T> listAll() {
        return findByCriteria();
    }

    /**
     * 分页查找数据
     *
     * @param page
     * @return
     */
    public PageInfo<T> list(PageInfo<T> page) {
        return findByCriteria(page);
    }

    /**
     * 按id获取对象，如果没有找到，返回NULL
     */
    public T get(Serializable id) {
        return (T) getSession().get(entityClass, id);
    }

    /**
     * 采用 dynamic model 方式，更具主键取得信息
     *
     * @param name
     * @param id
     * @return
     */
    public Map<String, Object> get(String name, Serializable id) {
        return (Map) getSession().get(name, id);
    }

    /**
     * 按HQL查询对象列表.
     *
     * @param hql    hql语句
     * @param values 数量可变的参数
     */
    public List<T> list(String hql, Object... values) {
        return createQuery(hql, values).list();
    }

    /**
     * 从缓存中取得查询结果，需要在model中配置二级缓存
     *
     * @param hql
     * @param values
     * @return
     */
    public List<T> listCache(String hql, Object... values) {
        return createQuery(hql, values).setCacheable(true).list();
    }

    /**
     * 按HQL分页查询.
     *
     * @param pageInfo 分页参数.包括pageSize 和firstResult.
     * @param hql      hql语句
     * @param values   数量可变的参数.
     * @return
     */
    public PageInfo<T> list(PageInfo<T> pageInfo, String hql, Object... values) {

        DynamicHql dynamicHql = getDynamicHql(pageInfo.getPostValue());

        logger.debug("dynamicHql=" + dynamicHql.getHql());

        String compileHql = compileHql(dynamicHql, hql, pageInfo.getOrderField(), pageInfo.getOrderType());

        logger.debug("compileHql=" + compileHql);

        List<Object> queryParameter = dynamicHql.getQueryData();

        Object[] queryValues = values;

        List<Object> dynamicQueryValues = new Vector<Object>();

        for (Object o : queryValues) {
            dynamicQueryValues.add(o);
        }
        for (Object o : queryParameter) {
            dynamicQueryValues.add(o);
        }

        if (pageInfo.isAutoCount()) {
            long total = countQueryResult(compileHql, dynamicQueryValues.toArray());
            pageInfo.setTotalCount((int) total);
            //如果记录数为0，不需要进行分页查询了
            if (total == 0) {
                pageInfo.setResult(new Vector());
                return pageInfo;
            }
        }
        Query q = createQuery(compileHql, dynamicQueryValues.toArray());
        if (pageInfo.isFirstSetted()) {
            q.setFirstResult(pageInfo.getFirst());
        }
        if (pageInfo.isPageSizeSetted()) {
            q.setMaxResults(pageInfo.getPageSize());
        }
        pageInfo.setResult(q.list());
        return pageInfo;
    }
    /*
    public PageInfo<T> list(PageInfo<T> pageInfo, String hql, Map queryData) {

        DynamicHql dynamicHql = getDynamicHql(pageInfo.getPostValue());

        logger.debug("dynamicHql=" + dynamicHql.getHql());

        String compileHql = compileHql(dynamicHql, hql, pageInfo.getOrderField(), pageInfo.getOrderType());

        logger.debug("compileHql=" + compileHql);

        List<Object> queryParameter = dynamicHql.getQueryData();

        Object[] queryValues = values;

        List<Object> dynamicQueryValues = new Vector<Object>();

        for (Object o : queryValues) {
            dynamicQueryValues.add(o);
        }
        for (Object o : queryParameter) {
            dynamicQueryValues.add(o);
        }

        if (pageInfo.isAutoCount()) {
            long total = countQueryResult(compileHql, dynamicQueryValues.toArray());
            pageInfo.setTotalCount((int) total);
            //如果记录数为0，不需要进行分页查询了
            if (total == 0) {
                pageInfo.setResult(new Vector());
                return pageInfo;
            }
        }
        Query q = createQuery(compileHql, dynamicQueryValues.toArray());
        if (pageInfo.isFirstSetted()) {
            q.setFirstResult(pageInfo.getFirst());
        }
        if (pageInfo.isPageSizeSetted()) {
            q.setMaxResults(pageInfo.getPageSize());
        }
        pageInfo.setResult(q.list());
        return pageInfo;
    }
    */

    public Long findLong(PageInfo<T> pageInfo, String hql, Object... values) {

        DynamicHql dynamicHql = getDynamicHql(pageInfo.getPostValue());

        String compileHql = compileHql1(dynamicHql, hql, pageInfo.getOrderField(), pageInfo.getOrderType());

        List<Object> queryParameter = dynamicHql.getQueryData();

        Object[] queryValues = values;

        List<Object> dynamicQueryValues = new Vector<Object>();

        for (Object o : queryValues) {
            dynamicQueryValues.add(o);
        }
        for (Object o : queryParameter) {
            dynamicQueryValues.add(o);
        }

        long total = longQueryResult(compileHql, dynamicQueryValues.toArray());
        return total;
    }

    /**
     * 查询条件预编译处理
     *
     * @param dynamicHql 动态hql对象
     * @param hql        用户调用的hql
     * @param orderField 排序字段
     * @param orderType  排序类型
     * @return
     */
    private String compileHql(DynamicHql dynamicHql, String hql, String orderField, String orderType) {
        String whereHql = dynamicHql.getHql();
        String orderHql = "";
        if (StringUtils.isNotEmpty(orderField) && StringUtils.isNotEmpty(orderType)) {
            orderHql = " " + orderField + " " + orderType + " ";
        }

        //分析原有的hql语句
        String oldHql = "";
        String oldOrder = "";
        int orderIndex = hql.indexOf("order");
        if (orderIndex == -1) {
            oldHql = hql;
        } else {
            String[] hqlArray = hql.split("order");
            oldHql = hqlArray[0];
            oldOrder = hqlArray[1].replaceAll("by", "");
        }

        if (oldHql.indexOf("where") == -1) {
            oldHql += " where ";
        } else {
            oldHql += " and ";
        }
        if (StringUtils.isNotEmpty(orderHql)) {
            oldOrder = orderHql + "," + oldOrder;
        }
//        return oldHql + whereHql;
        return oldHql + whereHql + " order by " + oldOrder;
    }


    private String compileHql1(DynamicHql dynamicHql, String hql, String orderField, String orderType) {
        String whereHql = dynamicHql.getHql();
        String orderHql = "";
        if (StringUtils.isNotEmpty(orderField) && StringUtils.isNotEmpty(orderType)) {
            orderHql = " " + orderField + " " + orderType + " ";
        }

        //分析原有的hql语句
        String oldHql = "";
        String oldOrder = "";
        int orderIndex = hql.indexOf("order");
        if (orderIndex == -1) {
            oldHql = hql;
        } else {
            String[] hqlArray = hql.split("order");
            oldHql = hqlArray[0];
            oldOrder = hqlArray[1].replaceAll("by", "");
        }

        if (oldHql.indexOf("where") == -1) {
            oldHql += " where ";
        } else {
            oldHql += " and ";
        }
        if (StringUtils.isNotEmpty(orderHql)) {
            oldOrder = orderHql + "," + oldOrder;
        }
        return oldHql + whereHql;
    }

    /**
     * 对动态的查询参数进行处理和转换
     *
     * @param searchParams
     * @return
     */
    private DynamicHql getDynamicHql(Map<String, Object> searchParams) {
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);

        //将Map数据转换为List
        List<SearchFilter> searchFilterList = new ArrayList<SearchFilter>();

        Iterator<String> filterKeys = filters.keySet().iterator();
        while (filterKeys.hasNext()) {
            String filterKey = filterKeys.next();
            searchFilterList.add(filters.get(filterKey));
        }
        return new DynamicHql(searchFilterList);
    }


    /**
     * 从缓存中取得数据库查询结果
     *
     * @param page
     * @param hql
     * @param values
     * @return
     */
    public PageInfo<T> listCache(PageInfo<T> page, String hql, Object... values) {
        if (page.isAutoCount()) {
            long total = countQueryResultCache(hql, values);
            page.setTotalCount((int) total);
            //如果记录数为0，不需要进行分页查询了
            if (total == 0) {
                page.setResult(new Vector());
                return page;
            }
        }
        Query q = createQuery(hql, values);
        if (page.isFirstSetted()) {
            q.setFirstResult(page.getFirst());
        }
        if (page.isPageSizeSetted()) {
            q.setMaxResults(page.getPageSize());
        }
        q.setCacheable(true);

        page.setResult(q.list());
        return page;
    }

    public long countQueryResult(String hql, Object... values) {
        String newHql = removeFetch(removeOrders(hql));
        String regex = "select\\s*(distinct[^,]*)(.*) from (.*)";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(newHql);
        if (m.find()) {
            newHql = newHql.replaceAll(regex, "select count($1) from $3");
        } else {
            newHql = "select count(*) " + removeSelect(newHql);
        }
        return findLong(newHql, values);
    }

    /**
     * hql查询，返回long
     *
     * @param newHql
     * @param values
     * @return
     */
    public long longQueryResult(String newHql, Object... values) {
        return findLong(newHql, values);
    }

    public long countQueryResultCache(String hql, Object... values) {
        String newHql = removeFetch(removeOrders(hql));
        String regex = "select\\s*(distinct[^,]*)(.*) from (.*)";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(newHql);
        if (m.find()) {
            newHql = newHql.replaceAll(regex, "select count($1) from $3");
        } else {
            newHql = "select count(*) " + removeSelect(newHql);
        }
        return findLongCache(newHql, values);
    }

    /**
     * 按HQL查询唯一对象.
     */
    public T findUnique(String hql, Object... values) {
        return (T) createQuery(hql, values).uniqueResult();
    }

    public T findUniqueCache(String hql, Object... values) {
        return (T) createQuery(hql, values).setCacheable(true).uniqueResult();
    }

    public T getSingleResult(String hql, Object... values) {
        List<T> list = createQuery(hql, values).list();
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /**
     * 按HQL查询Intger类形结果.
     */
    public Integer findInt(String hql, Object... values) {
        return (Integer) findUnique(hql, values);
    }

    /**
     * 按HQL查询Long类型结果.
     */
    public Long findLong(String hql, Object... values) {
        return (Long) findUnique(hql, values);
    }

    public Long findLongCache(String hql, Object... values) {
        return (Long) findUniqueCache(hql, values);
    }

    /**
     * 按Criterion查询对象列表.
     *
     * @param criterion 数量可变的Criterion.
     */
    public List<T> findByCriteria(Criterion... criterion) {
        return createCriteria(criterion).list();
    }

    /**
     * 按Criterion分页查询.
     *
     * @param pageInfo  分页参数.包括pageSize、firstResult、orderBy、asc、autoCount.
     *                  其中firstResult可直接指定,也可以指定pageNo.
     *                  autoCount指定是否动态获取总结果数.
     * @param criterion 数量可变的Criterion.
     * @return 分页查询结果.附带结果列表及所有查询时的参数.
     */
    public PageInfo<T> findByCriteria(PageInfo pageInfo, Criterion... criterion) {
        Criteria c = createCriteria(criterion);
        Map<String, Object> searchParams = pageInfo.getPostValue();

        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);

        DynamicCriterion dynamicCriterion = new DynamicCriterion(filters.values(), c);
//        dynamicCriterion.searchFilter();

        return findByCriteria(pageInfo, dynamicCriterion.getCriteria(), criterion);
    }

    /**
     * Criterion分页查询
     *
     * @param pageInfo
     * @param c
     * @param criterion
     * @return
     */
    public PageInfo<T> findByCriteria(PageInfo pageInfo, Criteria c, Criterion... criterion) {
        if (pageInfo.isAutoCount()) {
            long total = countQueryResult(pageInfo, c);
            pageInfo.setTotalCount((int) total);
            //如果记录数为0，不需要进行分页查询了
            if (total == 0) {
                pageInfo.setResult(new Vector());
                return pageInfo;
            }
        }
        if (pageInfo.isFirstSetted()) {
            c.setFirstResult(pageInfo.getFirst());
        }
        if (pageInfo.isPageSizeSetted()) {
            c.setMaxResults(pageInfo.getPageSize());
        }

        if (pageInfo.getOrderField() != null && pageInfo.getOrderType() != null && !pageInfo.getOrderField().equals("") && !pageInfo.getOrderType().equals("")) {
            if (pageInfo.getOrderType().endsWith(PageInfo.ASC)) {
                c.addOrder(Order.asc(pageInfo.getOrderField()));
            } else {
                c.addOrder(Order.desc(pageInfo.getOrderField()));
            }
        }

        if (pageInfo.isOrderBySetted()) {
            if (pageInfo.getOrder().endsWith(PageInfo.ASC)) {
                c.addOrder(Order.asc(pageInfo.getOrderBy()));
            } else {
                c.addOrder(Order.desc(pageInfo.getOrderBy()));
            }
        }
        pageInfo.setResult(c.list());
        return pageInfo;
    }

    /**
     * 按属性查找对象列表.
     */
    public List<T> listByProperty(String propertyName, Object value) {
        return createCriteria(Restrictions.eq(propertyName, value)).list();
    }

    /**
     * 按属性查找唯一对象.
     */
    public T findOneByProperty(String propertyName, Object value) {
        List<T> list = listByProperty(propertyName, value);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /**
     * 根据查询函数与参数列表创建Query对象,后续可进行更多处理,辅助函数.
     */
    public Query createQuery(String queryString, Object... values) {
        Query queryObject = getSession().createQuery(queryString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                queryObject.setParameter(i, values[i]);
            }
        }
        return queryObject;
    }

    /**
     * 根据Criterion条件创建Criteria,后续可进行更多处理,辅助函数.
     */
    public Criteria createCriteria(Criterion... criterions) {
        Criteria criteria = getSession().createCriteria(entityClass);
        for (Criterion c : criterions) {
            if (c != null) {
                criteria.add(c);
            }
        }
        return criteria;
    }

    /**
     * 判断对象的属性值在数据库内是否唯一.
     * <p/>
     * 在修改对象的情景下,如果属性新修改的值(value)等于属性原值(orgValue)则不作比较.
     * 传回orgValue的设计侧重于从页面上发出Ajax判断请求的场景.
     */
    public boolean isPropertyUnique(String propertyName, Object newValue, Object orgValue) {
        if (newValue == null || newValue.equals(orgValue)) {
            return true;
        }

        Object object = findOneByProperty(propertyName, newValue);
        return (object == null);
    }

    /**
     * 通过count查询获得本次查询所能获得的对象总数.
     *
     * @return page对象中的totalCount属性将赋值.
     */
    protected long countQueryResult(PageInfo<T> page, Criteria c) {
        CriteriaImpl impl = (CriteriaImpl) c;

        // 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
        Projection projection = impl.getProjection();
        ResultTransformer transformer = impl.getResultTransformer();

        List<CriteriaImpl.OrderEntry> orderEntries = null;
        try {
            orderEntries = (List) BeanUtils.getFieldValue(impl, "orderEntries");
            BeanUtils.setFieldValue(impl, "orderEntries", new ArrayList());
        } catch (Exception e) {
            logger.error("不可能抛出的异常:{}", e.getMessage());
        }


        // 执行Count查询
        long totalCount = (Long) c.setProjection(Projections.rowCount()).uniqueResult();

        // 将之前的Projection和OrderBy条件重新设回去
        c.setProjection(projection);

        if (projection == null) {
            c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        }
        if (transformer != null) {
            c.setResultTransformer(transformer);
        }

        try {
            BeanUtils.setFieldValue(impl, "orderEntries", orderEntries);
        } catch (Exception e) {
            logger.error("不可能抛出的异常:{}", e.getMessage());
        }

        return totalCount;
    }

    /**
     * 去除orderby 子句
     */
    public static String removeOrders(String hql) {
        Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(hql);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "");
        }
        m.appendTail(sb);
        return sb.toString();
    }

    public static String removeFetch(String hql) {
        Pattern p = Pattern.compile("join\\s*fetch*", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(hql);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "join");
        }
        m.appendTail(sb);
        return sb.toString();
    }

    public static String removeSelect(String hql) {
        int beginPos = hql.toLowerCase().indexOf("from");
        return hql.substring(beginPos);
    }
}
