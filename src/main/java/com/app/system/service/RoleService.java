package com.app.system.service;

import com.app.sqds.hibernate.HibernateDao;
import com.app.sqds.util.StringUtils;
import com.app.system.model.Menu;
import com.app.system.model.Role;
import com.app.system.model.RoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色
 * @author ccj
 */
@Service
public class RoleService extends HibernateDao<Role> {
    @Autowired private RoleMenuService roleMenuService;
    @Autowired private MenuService menuService;

    public List<Role> list() {
        String hql = "from Role r order by r.sort, r.id";
        return list(hql, new HashMap<>());
    }

    @CacheEvict(value = {"role","menu"}, allEntries = true)
    public void saveRole(Role role, String[] menuIdArray) {
        save(role);
        roleMenuService.deleteByRoleId(role.getId());
        for(String menuId : menuIdArray){
            if(StringUtils.isEmpty(menuId)){
                continue;
            }
            Menu menu = menuService.get(new Integer(menuId));
            if(menu!=null){
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRole(role);
                roleMenu.setMenu(menu);
                roleMenuService.save(roleMenu);
            }
        }
    }

    @CacheEvict(value = {"role","menu"}, allEntries = true)
    public void deleteRole(int roleId) {
        roleMenuService.deleteByRoleId(roleId);
        delete(roleId);
    }

    /**
     * 取得指定用户的所有角色
     * @param userId
     * @return
     */
    @Cacheable(value = "role")
    public List<Role> findUserRole(int userId){
        String hql = "select r from UserRole ur, Role r where ur.role.id=r.id and ur.user.id=:userId ";
//        return getSession().createQuery(hql).setInteger(0,userId).list();
        Map<String,Object> values = new HashMap<>();
        values.put("userId",userId);
        return list(hql, values);
    }
}
