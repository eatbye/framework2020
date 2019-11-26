package com.app.system.service;

import com.app.sqds.hibernate.HibernateDao;
import com.app.system.model.RoleMenu;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleMenuService extends HibernateDao<RoleMenu> {
    public List<RoleMenu> roleMenuList(int roleId) {
        String hql = "from RoleMenu rm where rm.role.id=?";
        return list(hql,roleId);
    }

    public void deleteByRoleId(Integer roleId) {
        String hql = "delete from RoleMenu where role.id=?";
        getSession().createQuery(hql).setInteger(0, roleId).executeUpdate();
    }
}
