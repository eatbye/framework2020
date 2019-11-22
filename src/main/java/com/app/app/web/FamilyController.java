package com.app.app.web;

import com.app.app.model.Family;
import com.app.app.service.FamilyService;
import com.app.sqds.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 家庭信息管理
 */
@Controller
@RequestMapping(Constant.VIEW_PREFIX + "family")
public class FamilyController {
    private Logger logger = LoggerFactory.getLogger(FamilyController.class);

    @Autowired private FamilyService familyService;

    @RequestMapping("list")
    public void list(){
        logger.debug("--------------");
    }

    @RequestMapping("listData")
    @ResponseBody
    public PageData listData(PageInfo<Family> pageInfo){
        pageInfo = familyService.familyList(pageInfo);
        return pageInfo.toPageData();
    }

    @RequestMapping("form")
    public void form(ModelMap modelMap){
        int id = ParamUtils.getInt("id",0);
        Family family = familyService.get(id);
        if(family == null){
            family = new Family();
        }
        modelMap.addAttribute("family", family);
    }

    @RequestMapping("save")
    @ResponseBody
    public SqdsResponse save() {
        int id = ParamUtils.getInt("id",0);
        Family family = familyService.get(id);
        if(family == null){
            family = new Family();
        }
        Servlets.bind(family);
        familyService.save(family);

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
        familyService.delete(id);
        return new SqdsResponse().success();
    }
}
