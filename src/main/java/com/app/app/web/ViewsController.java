package com.app.app.web;

import com.app.sqds.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("nepadmin/views")
public class ViewsController {
    private Logger logger = LoggerFactory.getLogger(ViewsController.class);

    @RequestMapping("layout")
    public void layout(){
        logger.debug("layout");
    }
}
