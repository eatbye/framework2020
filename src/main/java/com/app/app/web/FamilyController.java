package com.app.app.web;

import com.app.app.model.Family;
import com.app.app.service.FamilyService;
import com.app.sqds.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Enumeration;

/**
 * 家庭信息管理
 */
@Controller
@RequestMapping(Constant.VIEW_PREFIX + "family")
public class FamilyController {
    private Logger logger = LoggerFactory.getLogger(FamilyController.class);

    @Autowired private FamilyService familyService;

    @RequestMapping("list.html")
    public void list(){
        logger.debug("--------------");
    }

    @RequestMapping("listData")
    @ResponseBody
    public PageData listData(PageInfo<Family> pageInfo){
        pageInfo = familyService.familyList(pageInfo);
        return pageInfo.toPageData();
    }

    @RequestMapping("form.html")
    public void form(){
        logger.debug("form...");
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
        logger.debug("family = {}", family);
        familyService.save(family);

        return new SqdsResponse().success();
    }
}
