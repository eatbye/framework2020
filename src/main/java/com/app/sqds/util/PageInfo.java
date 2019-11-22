package com.app.sqds.util;

import com.app.sqds.exception.SqdsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * 分页信息
 * @author ccj
 *
 * @param <T>
 */
public class PageInfo<T> {
    protected static final Logger logger = LoggerFactory.getLogger(PageInfo.class);

    public static final String ASC = "asc";
    public static final String DESC = "desc";

    private int pageNo = 1;
    private int pageSize = 15;

    private String orderField;
    private String orderType = DESC;

    private String orderBy = "";
    private String order = DESC;

    private boolean autoCount = true;
    private List<T> result = null;
    private int totalCount = -1;

    private Map<String,Object> postValue = new HashMap<String,Object>(); //存放查询条件

    public PageInfo(){
        HttpServletRequest request =((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Servlets.initPageInfo(request, this);
    }
    /**
     * 获得每页的记录数量,无默认值.
     */
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 是否已设置每页的记录数量.
     */
    public boolean isPageSizeSetted() {
        return pageSize > -1;
    }

    /**
     * 获得当前页的页号,序号从1开始,默认为1.
     */
    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    /**
     * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从0开始.
     */
    public int getFirst() {
        if (pageNo < 1 || pageSize < 1)
            return -1;
        else
            return ((pageNo - 1) * pageSize);
    }

    /**
     * 是否已设置第一条记录记录在总结果集中的位置.
     */
    public boolean isFirstSetted() {
        return (pageNo > 0 && pageSize > 0);
    }

    /**
     * 获得排序字段,无默认值.
     */
    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    /**
     * 是否已设置排序字段.
     */
    public boolean isOrderBySetted() {
        boolean order = StringUtils.isNotEmpty(orderBy);
        if(StringUtils.isNotEmpty(orderField) && StringUtils.isNotEmpty(orderBy) && orderField.equals(orderBy)){
            order = false;
        }
        return order;
    }

    /**
     * 获得排序方向,默认为asc.
     */
    public String getOrder() {
        return order;
    }
    /**
     * 添加查询条件
     * @param key
     * @param value
     */
    public void addParameter(String key,Object value){
        postValue.put(key,value);
    }
    /**
     * 删除查询条件
     * @param key
     */
    public void removeParameter(String key){
        postValue.remove(key);
    }
    /**
     * 清除所有查询条件
     */
    public void clearParameter(){
        postValue.clear();
    }

    public Object getParameter(String key){
        return postValue.get(key);
    }

    public String getStringParameter(String key){
        return (String)postValue.get(key);
    }

    public Integer getIntegerParameter(String key){
        Integer value = null;
        try {
            value = new Integer(getStringParameter(key));
        }catch (SqdsException e){
            logger.debug(e.getMessage());
        }
        return value;
    }

    public Long getLongParameter(String key){
        Long value = null;
        try {
            value = new Long(getStringParameter(key));
        }catch (SqdsException e){
            logger.debug(e.getMessage());
        }
        return value;
    }

    public BigDecimal getBigDecimalParameter(String key){
        BigDecimal value = null;
        try {
            value = new BigDecimal(getStringParameter(key));
        }catch (SqdsException e){
            logger.debug(e.getMessage());
        }
        return value;
    }


    public Object search(String key){
        if(key==null){
            return "";
        }
//        if(key.startsWith("search_")){
//            key = key.substring(7);
//        }
        return postValue.get(key);
    }

    public void setPostValue(Map<String, Object> postValue) {
        this.postValue = postValue;
    }

    public Map<String,Object> getPostValue(){
        return postValue;
    }

    /**
     * 设置排序方式向.
     *
     * @param order 可选值为desc或asc.
     */
    public void setOrder(String order) {
        if (ASC.equalsIgnoreCase(order) || DESC.equalsIgnoreCase(order)) {
            this.order = order.toLowerCase();
        } else
            throw new IllegalArgumentException("order should be 'desc' or 'asc'");
    }

    /**
     * 是否自动获取总页数,默认为true.
     * 注意本属性仅于query by Criteria时有效,query by HQL时本属性无效.
     */
    public boolean isAutoCount() {
        return autoCount;
    }

    public void setAutoCount(boolean autoCount) {
        this.autoCount = autoCount;
    }

    /**
     * 页内的数据列表.
     */
    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    /**
     * 总记录数.
     */
    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * 计算总页数.
     */
    public int getTotalPages() {
        if (totalCount == -1)
            return -1;

        int count = totalCount / pageSize;
        if (totalCount % pageSize > 0) {
            count++;
        }
        return count;
    }

    /**
     * 是否还有下一页.
     */
    public boolean isHasNext() {
        return (pageNo + 1 <= getTotalPages());
    }

    /**
     * 返回下页的页号,序号从1开始.
     */
    public int getNextPage() {
        if (isHasNext())
            return pageNo + 1;
        else
            return pageNo;
    }

    /**
     * 是否还有上一页.
     */
    public boolean isHasPre() {
        return (pageNo - 1 >= 1);
    }

    /**
     * 返回上页的页号,序号从1开始.
     */
    public int getPrePage() {
        if (isHasPre())
            return pageNo - 1;
        else
            return pageNo;
    }

    public boolean hasPreviousPage(){
        if(getPageNo()-1>0){
            return true;
        }else{
            return false;
        }
    }

    public boolean hasNextPage(){
        if(getTotalPages()>getPageNo()){
            return true;
        }else{
            return false;
        }
    }

//    public String getSearchParams(){
//        String searchParams = Servlets.encodeParameterStringWithPrefix(getPostValue(), "");
//        searchParams = searchParams +"&_pageNo="+pageNo;
//        return searchParams;
//    }

    /**
     * 自定义排序字段
     * @return
     */
    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    /**
     * 自定义排序类型，默认desc
     * @return
     */
    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public PageData toPageData(){
        PageData pageData = new PageData();
        pageData.setData(this.getResult());
        pageData.setCode(0);
        pageData.setCount(getTotalCount());
        return pageData;
    }

    public String getPostStringValue(String name){
        String value = (String)postValue.get(name);
        if(value == null){
            value = "";
        }
        return value;
    }
}
