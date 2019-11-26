package com.app.system.service;

import com.app.sqds.hibernate.HibernateDao;
import com.app.system.model.Role;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色
 * @author ccj
 */
@Service
public class RoleService extends HibernateDao<Role> {
    public List<Role> list() {
        String hql = "from Role r order by r.sort, r.id";
        return list(hql);
    }
}
