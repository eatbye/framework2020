package com.app.system.web;

import com.app.sqds.util.Constant;
import com.app.sqds.util.ParamUtils;
import com.app.sqds.util.Servlets;
import com.app.sqds.util.SqdsResponse;
import com.app.system.model.Department;
import com.app.system.model.Menu;
import com.app.system.service.DepartmentService;
import com.app.system.util.MenuTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 部门管理
 * @author ccj
 */
@Controller
@RequestMapping(Constant.VIEW_PREFIX + "system/department")
public class DepartmentController {
    private Logger logger = LoggerFactory.getLogger(DepartmentController.class);
    @Autowired private DepartmentService departmentService;

    @RequestMapping("index")
    public void index(){
        logger.debug("department");
    }

    @RequestMapping("tree")
    @ResponseBody
    public SqdsResponse tree() {
        MenuTree<Department> departments = this.departmentService.findDepartment();
        return new SqdsResponse().success().data(departments.getChilds());
    }

    @RequestMapping("update")
    @ResponseBody
    public SqdsResponse update() {
        int departmentId = ParamUtils.getInt("departmentId",0);
        int parentId = ParamUtils.getInt("parentId",0);
        Department department = departmentService.get(departmentId);
        if(department == null){
            department = new Department();
        }
        Servlets.bind(department);
        Department parentDepartment = departmentService.get(parentId);
        parentDepartment.setParentDepartment(department);
        departmentService.save(department);

        return new SqdsResponse().success();
    }

    @RequestMapping("delete")
    @ResponseBody
    public SqdsResponse delete() {
        String departmentIds = ParamUtils.getString("departmentIds","");
        departmentService.deleteDepartment(departmentIds);
        return new SqdsResponse().success();
    }

}
