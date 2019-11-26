package com.app.system.web;

import com.app.app.model.Family;
import com.app.app.web.FamilyController;
import com.app.sqds.util.*;
import com.app.system.model.Department;
import com.app.system.model.Role;
import com.app.system.model.User;
import com.app.system.model.UserRole;
import com.app.system.service.DepartmentService;
import com.app.system.service.RoleService;
import com.app.system.service.UserRoleService;
import com.app.system.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Vector;

/**
 * 用户管理
 * @author ccj
 */
@Controller
@RequestMapping(Constant.VIEW_PREFIX + "system/user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired private UserService userService;
    @Autowired private DepartmentService departmentService;
    @Autowired private UserRoleService userRoleService;
    @Autowired private RoleService roleService;

    @RequestMapping("list")
    public void list(){

    }

    @RequestMapping("listData")
    @ResponseBody
    public PageData listData(PageInfo<User> pageInfo){
        pageInfo = userService.list(pageInfo);
        return pageInfo.toPageData();
    }

    @RequestMapping("form")
    public void form(ModelMap modelMap){
        int id = ParamUtils.getInt("id",0);
        User user = userService.get(id);
        if(user == null){
            user = new User();
            user.setValid(1);
        }
        List<UserRole> userRoleList = userRoleService.getListByUserId(id);
        String roleId = "";
        for(UserRole userRole : userRoleList){
            roleId += userRole.getRole().getId();
            roleId += ",";
        }
        modelMap.addAttribute("user", user);
        modelMap.addAttribute("roleId", roleId);
    }

    /**
     * 用户信息修改，更新用户部门及用户角色
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public SqdsResponse save() {
        int id = ParamUtils.getInt("id",0);
        String departmentId = ParamUtils.getString("departmentId","");
        String roleId = ParamUtils.getString("roleId","");

        Department department = departmentService.get(new Integer(departmentId));

        List<UserRole> userRoleList = new Vector<>();
        String[] roleIdArray = roleId.split(",");
        for(String rid : roleIdArray){
            if(StringUtils.isNotEmpty(rid)){
                Role role = roleService.get(new Integer(rid));
                if(role != null){
                    UserRole userRole = new UserRole();
                    userRole.setRole(role);
                    userRoleList.add(userRole);
                }
            }
        }

        User user = userService.get(id);
        if(user == null){
            user = new User();
        }
        Servlets.bind(user);
        user.setDepartment(department);

        userService.saveUser(user, userRoleList);

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
//        userService.delete(id);
        userService.deleteUser(id);
        return new SqdsResponse().success();
    }
}
