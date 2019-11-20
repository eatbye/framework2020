package com.app.app.web;

import com.app.app.model.Family;
import com.app.app.service.FamilyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("demo")
public class DemoController {
    private Logger logger = LoggerFactory.getLogger(DemoController.class);

    @Autowired private FamilyService familyService;

    @RequestMapping("demo")
    public void demo(){
        logger.debug("demo");
        List<Family> familyList = familyService.familyList();
        logger.debug("familyList.size = {}", familyList.size());
    }
}
