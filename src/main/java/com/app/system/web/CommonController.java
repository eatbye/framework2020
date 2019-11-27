package com.app.system.web;

import com.app.sqds.util.ParamUtils;
import com.app.sqds.util.SqdsResponse;
import com.app.sqds.util.StringUtils;
import com.app.system.model.User;
import com.app.system.util.CaptchaUtil;
import com.wf.captcha.base.Captcha;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户登录
 */
@Controller
@RequestMapping
public class CommonController {
    private Logger logger = LoggerFactory.getLogger(CommonController.class);

    /**
     * 用户登录
     */
    @RequestMapping("login/login")
    public void login(){

    }

    /**
     * 登录检测
     * @return
     */
    @RequestMapping("login/checkLogin")
    @ResponseBody
    public SqdsResponse checkLogin(){
        String username = ParamUtils.getString("username","");
        String password = ParamUtils.getString("password","");
        String verifyCode = ParamUtils.getString("verifyCode","");

        String code = (String)ParamUtils.getRequest().getSession().getAttribute("code");
        if(StringUtils.isEmpty(code)){
            return new SqdsResponse().fail().message("请刷新页面获取验证码");
        }

        if(!verifyCode.equals(code)){
            return new SqdsResponse().fail().message("验证码输入错误");
        }

        logger.debug("username = {}, password = {}", username, password);

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        // 获取Subject对象
        Subject subject = SecurityUtils.getSubject();

        logger.debug("subject = {}", subject);
        try {
            subject.login(token);
            return new SqdsResponse().success();
        } catch (UnknownAccountException e) {
            return new SqdsResponse().fail().message(e.getMessage());
        } catch (IncorrectCredentialsException e) {
            return new SqdsResponse().fail().message(e.getMessage());
        } catch (LockedAccountException e) {
            return new SqdsResponse().fail().message(e.getMessage());
        } catch (AuthenticationException e) {
            return new SqdsResponse().fail().message("认证失败！");
        }
    }

    @RequestMapping({"index","/"})
    public String index(Model model){
        // 登录成后，即可通过Subject获取登录的用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("user", user);

        logger.debug("user = {}", user);
        return "index";
    }

    @GetMapping("images/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CaptchaUtil.outPng(110, 34, 4, Captcha.TYPE_ONLY_NUMBER, request, response);
    }

    @RequestMapping("nepadmin/views/layout")
    public void layout(Model model){
        // 登录成后，即可通过Subject获取登录的用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("user", user);

        logger.debug("layout");
    }
}
