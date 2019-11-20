package com.app.app.web;

import com.app.app.model.Family;
import com.app.app.service.FamilyService;
import com.app.sqds.util.Constant;
import com.app.sqds.util.SqdsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
    public SqdsResponse listData(){
        List<Family> familyList = familyService.familyList();
        return new SqdsResponse().success().data(familyList);
    }
}
