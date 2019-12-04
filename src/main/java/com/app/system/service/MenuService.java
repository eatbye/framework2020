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

import java.util.*;

/**
 * 菜单管理
 * @author ccj
 */
@Service
public class MenuService extends HibernateDao<Menu> {
    private Logger logger = LoggerFactory.getLogger(MenuService.class);

    public List<MenuTree> getMenuTree(Integer userId){

        String hql = "select distinct m from User u, UserRole ur, Role r, RoleMenu rm, Menu m where u.id=ur.user.id and ur.role.id = r.id and r.id=rm.role.id" +
                " and rm.menu.id=m.id and m.type=1 and u.id=:userId order by m.sort";

        Map<String,Object> values = new HashMap<>();
        values.put("userId",userId);

        List<Menu> menuList = list(hql, values);

        List<MenuTree> menuTreeList = new Vector<>();

        for(Menu menu : menuList){
            if(menu.getParentMenu() == null){
                MenuTree menuTree = new MenuTree();
                menuTree.setId(menu.getId()+"");
                menuTree.setIcon(menu.getIcon());
                menuTree.setTitle(menu.getMenuName());
                menuTreeList.add(menuTree);
            }
        }
        for(MenuTree menuTree : menuTreeList){
            List<MenuTree> childMenuTreeList = new Vector<>();
            for(Menu menu : menuList){
                if(menu.getParentMenu() != null && (menu.getParentMenu().getId()+"").equals(menuTree.getId())){
                    MenuTree childMenuTree = new MenuTree();
                    childMenuTree.setHref(menu.getUrl());
                    childMenuTree.setTitle(menu.getMenuName());
                    childMenuTreeList.add(childMenuTree);
                    menuTree.setChilds(childMenuTreeList);
                }
            }
        }

        return menuTreeList;
    }

    public MenuTree<Menu> findMenus() {
        String hql = "from Menu order by sort,id";
        List<Menu> menuList = list(hql, new HashMap<>());
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
        String hql = "select m from UserRole ur, User u, Role r, Menu m, RoleMenu rm where ur.user.id=u.id and ur.role.id=r.id and r.id=rm.role.id and rm.menu.id=m.id and u.id=:userId";
        Map<String,Object> values = new HashMap<>();
        values.put("userId",userId);
        return list(hql, values);
    }

    @CacheEvict(value = {"role","menu"}, allEntries = true)
    public void saveMenu(Menu menu) {
        save(menu);
    }
}
