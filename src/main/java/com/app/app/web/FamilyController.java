package com.app.app.web;

import com.app.app.model.Family;
import com.app.app.service.FamilyService;
import com.app.sqds.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

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
    public PageData listData(HttpServletRequest request){
        PageInfo<Family> pageInfo = new PageInfo<Family>();
        Servlets.initPageInfo(request,pageInfo);

        pageInfo = familyService.familyList(pageInfo);

        return pageInfo.toPageData();

    }
}
