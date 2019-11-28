package com.app.system.web;

import com.app.sqds.util.Constant;
import com.app.sqds.util.PageData;
import com.app.sqds.util.ParamUtils;
import com.app.sqds.util.SqdsResponse;
import com.app.system.model.ActiveUser;
import com.app.system.service.SessionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统监控
 * @author ccj
 */
@Controller
@RequestMapping(Constant.VIEW_PREFIX + "system/monitor")
public class MonitorController {
    private Logger logger = LoggerFactory.getLogger(MonitorController.class);

    @Autowired private SessionService sessionService;

    @RequestMapping("online")
    public void online() {
        logger.debug("online");
    }


    @RequestMapping("onlineList")
    @ResponseBody
    public PageData list(String username) {
        List<ActiveUser> list = sessionService.list();
        PageData pageData = new PageData();
        pageData.setData(list);
        pageData.setCode(0);
        pageData.setCount(list.size());
        return pageData;
    }


    /**
     * 踢出用户
     * @return
     */
    @RequestMapping("deleteSession")
    public SqdsResponse deleteSession() {
        String id = ParamUtils.getString("id","");
        sessionService.forceLogout(id);
        return new SqdsResponse().success();

    }
}
