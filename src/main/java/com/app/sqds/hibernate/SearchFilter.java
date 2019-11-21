package com.app.sqds.hibernate;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.app.sqds.util.DateUtils;
import com.app.sqds.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 查询过滤处理，对sql的常用操作符和html input数据进行转换处理
 */
public class SearchFilter {

    private static Logger logger = LoggerFactory.getLogger(SearchFilter.class);

    /**
     * 数据库操作符号
     * eq =
     * like like
     * gt >
     * lt <
     * ge >=
     * le <=
     */
    public enum Operator {
        eq, like, gt, lt, ge, le
    }

    /**
     * 数据类型简写
     * s string
     * i integer
     * l long
     * b bigDecimal
     * d date
     */
    public enum FieldType{
        string,integer,bigDecimal,date
    }

    public String fieldName;    //hibernate的属性，对应数据库的字段，支持别名
    public Object value;        //查询数值
    public Operator operator;   //操作符号

    /**
     * 构造 searchFilter
     * @param fieldName
     * @param operator
     * @param value
     */
    public SearchFilter(String fieldName, Operator operator, Object value) {
        this.fieldName = fieldName;
        this.value = value;
        this.operator = operator;
    }

    /**
     * 解析 pageInfo中的数据，初步整理为hibernate可识别的数据
     */
    public static Map<String, SearchFilter> parse(Map<String, Object> searchParams) {
        Map<String, SearchFilter> filters = new HashMap<>();

        logger.debug("search parse...");
        for (Entry<String, Object> entry : searchParams.entrySet()) {
            // 过滤掉空值
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value==null) {
                continue;
            }

            if(key.startsWith("search_")){
                key = key.substring(7);
            }else{
                continue;
            }

            // 拆分operator与filedAttribute
            String[] names = StringUtils.split(key, "_");
            if(names.length!=3){
                continue;
//                throw new SqdsException(key + " is not a valid search filter name.");
            }
            Operator operator = Operator.valueOf(names[0]);
            FieldType fieldType = FieldType.valueOf(names[1]);
            String fieldName = names[2];

            // 创建searchFilter
            Object targetValue = getValue(fieldType,value.toString());
            if(targetValue!=null){
                SearchFilter filter = new SearchFilter(fieldName, operator, targetValue);
                filters.put(key, filter);
            }

        }

        return filters;
    }


    public static Object getValue(FieldType fieldType,String object){
        Object target = null;
        try {
            if (fieldType == FieldType.string) {
                target = object;
            } else if (fieldType == FieldType.integer) {
                target = new Integer(object);
            } else if (fieldType == FieldType.bigDecimal) {
                target = new BigDecimal(object);
            } else if (fieldType == FieldType.date) {
                target = DateUtils.string2date(object);
            }
        } catch (Exception e) {
            logger.warn("fieldType="+fieldType+" : "+e.getMessage());
        }
        /*

        try {
            //TODO 增加缓存处理
//            Field field = clazz.getDeclaredField(fieldName);
            String fieldClass = field.getGenericType().toString();
            if(fieldClass.equals("class java.lang.String")){
                target = object;
            }else if(fieldClass.equals("class java.lang.Integer")){
                target = new Integer(object);
            }else if(fieldClass.equals("class java.lang.Long")){
                target = new Long(object);
            }else if(fieldClass.equals("class java.lang.Float")){
                target = new Float(object);
            }else if(fieldClass.equals("class java.lang.Double")){
                target = new Double(object);
            }else if(fieldClass.equals("class java.lang.BigDecimal")){
                target = new BigDecimal(object);
            }else if(fieldClass.equals("class java.util.Date")){
                target = DateUtils.stringToDate(object);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        */
        return target;
    }
}