package com.app.system.service;

import com.app.sqds.hibernate.HibernateDao;
import com.app.system.model.RoleMenu;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleMenuService extends HibernateDao<RoleMenu> {
    public List<RoleMenu> roleMenuList(int roleId) {
        String hql = "from RoleMenu rm where rm.role.id=:roleId";
        Map<String,Object> values = new HashMap<>();
        values.put("roleId", roleId);
        return list(hql,values);
    }

    @CacheEvict(value = {"role","menu"}, allEntries = true)
    public void deleteByRoleId(Integer roleId) {
        String hql = "delete from RoleMenu where role.id=?0";
        getSession().createQuery(hql).setParameter(0, roleId).executeUpdate();
    }

}
