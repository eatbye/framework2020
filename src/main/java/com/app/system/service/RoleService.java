package com.app.system.service;

import com.app.sqds.hibernate.HibernateDao;
import com.app.sqds.util.StringUtils;
import com.app.system.model.Menu;
import com.app.system.model.Role;
import com.app.system.model.RoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return list(hql);
    }

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

    public void deleteRole(int roleId) {
        roleMenuService.deleteByRoleId(roleId);
        delete(roleId);
    }
}
