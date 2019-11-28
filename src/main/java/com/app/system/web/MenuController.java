package com.app.system.web;

import com.app.sqds.util.Constant;
import com.app.sqds.util.ParamUtils;
import com.app.sqds.util.Servlets;
import com.app.sqds.util.SqdsResponse;
import com.app.system.model.Menu;
import com.app.system.service.MenuService;
import com.app.system.util.MenuTree;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 菜单管理
 */
@Controller
@RequestMapping(Constant.VIEW_PREFIX + "system/menu")
public class MenuController {
    private Logger logger = LoggerFactory.getLogger(MenuController.class);
    @Autowired private MenuService menuService;

    @RequestMapping("menu")
    public void menu(){
        logger.debug("menu");
    }

    @RequestMapping("tree")
    @ResponseBody
    public SqdsResponse tree() {
        MenuTree<Menu> menus = this.menuService.findMenus();
        return new SqdsResponse().success().data(menus.getChilds());
    }

    @RequestMapping("update")
    @ResponseBody
    public SqdsResponse update() {
        int menuId = ParamUtils.getInt("menuId",0);
        int parentId = ParamUtils.getInt("parentId",0);
        Menu menu = menuService.get(menuId);
        if(menu == null){
            menu = new Menu();
        }
        Servlets.bind(menu);
        Menu parentMenu = menuService.get(parentId);
        menu.setParentMenu(parentMenu);
        menuService.saveMenu(menu);
        return new SqdsResponse().success();
    }

    @RequestMapping("delete")
    @ResponseBody
    public SqdsResponse delete() {
        String menuId = ParamUtils.getString("menuId","");
        menuService.deleteMenus(menuId);
        return new SqdsResponse().success();
    }

    @RequestMapping("menuIcon")
    public void menuIcon() {
    }
}
