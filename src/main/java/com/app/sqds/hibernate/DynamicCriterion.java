package com.app.sqds.hibernate;

import com.app.sqds.util.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import java.util.Collection;

/**
 * 动态criterion拼装处理
 * Created by cai on 2016/2/19.
 */
public class DynamicCriterion {

    private Collection<SearchFilter> filters;
    private Criteria criteria;

    public DynamicCriterion(Collection<SearchFilter> filters, Criteria criteria) {
        this.filters = filters;
        this.criteria = criteria;
        searchFilter();
    }

    public Criteria getCriteria() {
        return criteria;
    }

    private void searchFilter() {
        for (SearchFilter filter : filters) {
            String expression = filter.fieldName;
            if (StringUtils.isEmpty(filter.value + "")) {
                continue;
            }
            switch (filter.operator) {
                case eq:
                    criteria.add(Restrictions.eq(expression, filter.value));
                    break;
                case like:
                    criteria.add(Restrictions.like(expression, filter.value + "", MatchMode.ANYWHERE));
                    break;
                case gt:
                    criteria.add(Restrictions.gt(expression, filter.value));
                    break;
                case lt:
                    criteria.add(Restrictions.lt(expression, filter.value));
                    break;
                case ge:
                    criteria.add(Restrictions.ge(expression, filter.value));
                    break;
                case le:
                    criteria.add(Restrictions.le(expression, filter.value));
                    break;
                default:
                    break;
            }
        }
    }
}
