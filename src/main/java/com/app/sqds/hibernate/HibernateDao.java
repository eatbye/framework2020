package com.app.sqds.hibernate;

import com.app.sqds.util.GenericsUtils;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

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
}
