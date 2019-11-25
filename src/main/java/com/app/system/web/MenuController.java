package com.app.system.web;

import com.app.sqds.util.Constant;
import com.app.sqds.util.ParamUtils;
import com.app.sqds.util.Servlets;
import com.app.sqds.util.SqdsResponse;
import com.app.system.model.Menu;
import com.app.system.service.MenuService;
import com.app.system.util.MenuTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public SqdsResponse updateMenu() {
        logger.debug("update...");
//        this.menuService.updateMenu(menu);
        int menuId = ParamUtils.getInt("menuId",0);
        int parentId = ParamUtils.getInt("parentId",0);
        logger.debug("parentId = {}",parentId);
        Menu menu = menuService.get(menuId);
        if(menu == null){
            menu = new Menu();
        }
        Servlets.bind(menu);
        Menu parentMenu = menuService.get(parentId);
        menu.setParentMenu(parentMenu);
        menuService.save(menu);
        logger.debug("menu = {}",menu);
        return new SqdsResponse().success();
    }

    @RequestMapping("delete")
    @ResponseBody
    public SqdsResponse deleteMenus() {
        String menuId = ParamUtils.getString("menuId","");
        menuService.deleteMenus(menuId);
        return new SqdsResponse().success();
    }
}
