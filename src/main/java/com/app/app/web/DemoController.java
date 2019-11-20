package com.app.app.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("demo")
public class DemoController {
    private Logger logger = LoggerFactory.getLogger(DemoController.class);

    @RequestMapping("demo")
    public void demo(){
        logger.debug("demo");
    }
}
