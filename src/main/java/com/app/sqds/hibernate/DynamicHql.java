package com.app.sqds.hibernate;

import com.app.sqds.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Vector;

/**
 * 动态hql拼装处理
 * Created by cai on 2016/2/19.
 */
public class DynamicHql {
    private Logger logger = LoggerFactory.getLogger(DynamicHql.class);
    private Collection<SearchFilter> filters;
    private String hql;
    private List<Object> queryData;


    public DynamicHql(Collection<SearchFilter> filters) {
        this.filters = filters;
        queryData = new Vector<Object>();
        hql = "";
        searchFilter();
    }

    public String getHql() {
        return hql;
    }

    public List<Object> getQueryData() {
        return queryData;
    }

    private void searchFilter() {
        for (SearchFilter filter : filters) {
            logger.debug("filter.value=" + filter.value);
            if (StringUtils.isEmpty(filter.value + "")) {
                continue;
            }
            String expression = filter.fieldName;
            switch (filter.operator) {
                case eq:
                    hql += expression + " = ? and ";
                    queryData.add(filter.value);
                    break;
                case like:
                    hql += expression + " like ? and ";
                    queryData.add("%" + filter.value + "%");
                    break;
                case gt:
                    hql += expression + " > ? and ";
                    queryData.add(filter.value);
                    break;
                case lt:
                    hql += expression + " < ? and ";
                    queryData.add(filter.value);
                    break;
                case ge:
                    hql += expression + " >= ? and ";
                    queryData.add(filter.value);
                    break;
                case le:
                    hql += expression + " <= ? and ";
                    queryData.add(filter.value);
                    break;
                default:
                    break;
            }
        }
        hql += " 1=1 ";

    }
}
