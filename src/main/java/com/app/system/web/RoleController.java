package com.app.system.web;

import com.app.app.model.Family;
import com.app.sqds.util.*;
import com.app.system.model.Dict;
import com.app.system.model.Role;
import com.app.system.service.DictService;
import com.app.system.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * 角色管理
 */
@Controller
@RequestMapping(Constant.VIEW_PREFIX + "system/role")
public class RoleController {
    private Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    @RequestMapping("list")
    public void list(){
        logger.debug("role list");
    }

    @RequestMapping("listData")
    @ResponseBody
    public PageData listData(){
        List<Role> roleList = roleService.list();
        PageData pageData = new PageData();
        pageData.setData(roleList);
        pageData.setCode(0);
        pageData.setCount(roleList.size());
        return pageData;
    }

    @RequestMapping("form")
    public void form(ModelMap modelMap){
        int id = ParamUtils.getInt("id",0);
        Role role = roleService.get(id);
        if(role == null){
            role = new Role();
        }
        modelMap.addAttribute("role", role);
    }

    @RequestMapping("save")
    @ResponseBody
    public SqdsResponse save() {
        int id = ParamUtils.getInt("id",0);
        Role role = roleService.get(id);
        if(role == null){
            role = new Role();
        }
        Servlets.bind(role);
        roleService.save(role);

        return new SqdsResponse().success();
    }

    @RequestMapping("view")
    public void view(ModelMap modelMap){
        form(modelMap);
    }

    @RequestMapping("delete")
    @ResponseBody
    public SqdsResponse delete() {
        int id = ParamUtils.getInt("id",0);
        roleService.delete(id);
        return new SqdsResponse().success();
    }
}
