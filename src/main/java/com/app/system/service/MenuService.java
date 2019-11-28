package com.app.system.service;

import com.app.sqds.hibernate.HibernateDao;
import com.app.system.model.Menu;
import com.app.system.util.MenuTree;
import com.app.system.util.TreeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单管理
 * @author ccj
 */
@Service
public class MenuService extends HibernateDao<Menu> {
    private Logger logger = LoggerFactory.getLogger(MenuService.class);

    public MenuTree<Menu> findMenus() {
        String hql = "from Menu order by sort,id";
        List<Menu> menuList = list(hql);
        logger.debug("menuList.size = {}", menuList.size());
        List<MenuTree<Menu>> trees = this.convertMenus(menuList);

        return TreeUtil.buildMenuTree(trees);
    }


    private List<MenuTree<Menu>> convertMenus(List<Menu> menus) {
        List<MenuTree<Menu>> trees = new ArrayList<>();
        menus.forEach(menu -> {
            MenuTree<Menu> tree = new MenuTree<>();
            tree.setId(String.valueOf(menu.getId()));

            logger.debug("parent = {}",menu.getParentMenu());
            if(menu.getParentMenu() != null){
                tree.setParentId(String.valueOf(menu.getParentMenu().getId()));
            }else{
                tree.setParentId("0");
            }
            tree.setTitle(menu.getMenuName());
            tree.setIcon(menu.getIcon());
            tree.setHref(menu.getUrl());
            tree.setData(menu);
            trees.add(tree);
        });
        return trees;
    }

    @CacheEvict(value = {"role","menu"}, allEntries = true)
    public void deleteMenus(String menuIds) {
        String[] menuIdArray = menuIds.split(",");
        for(String menuId : menuIdArray){
            delete(new Integer(menuId));
        }
    }

    @Cacheable(value = "menu")
    public List<Menu> findUserPermissions(Integer userId) {
        String hql = "select m from UserRole ur, User u, Role r, Menu m, RoleMenu rm where ur.user.id=u.id and ur.role.id=r.id and r.id=rm.role.id and rm.menu.id=m.id and u.id=?";
        return list(hql,userId);
    }

    @CacheEvict(value = {"role","menu"}, allEntries = true)
    public void saveMenu(Menu menu) {
        save(menu);
    }
}
