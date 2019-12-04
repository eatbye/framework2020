package com.app.system.service;

import com.app.sqds.hibernate.HibernateDao;
import com.app.system.model.UserRole;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户角色
 */
@Service
public class UserRoleService extends HibernateDao<UserRole> {

    @CacheEvict(value = {"role","menu"}, allEntries = true)
    public void deleteByUserId(Integer userId) {
        String hql = "delete from UserRole where user.id=:userId";
        getSession().createQuery(hql).setParameter("userId", userId).executeUpdate();
    }

    public List<UserRole> getListByUserId(int userId) {
        String hql = "from UserRole ur where ur.user.id=:userId";
        Map<String,Object> values = new HashMap<>();
        values.put("userId", userId);
        return list(hql,values);
    }
}
