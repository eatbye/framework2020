package com.app.system.config;


import com.app.system.authentication.freemarker.ShiroTags;
import freemarker.template.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

@Component
public class ShiroTagFreeMarkerConfig implements InitializingBean {
    private Logger logger = LoggerFactory.getLogger(ShiroTagFreeMarkerConfig.class);
    @Autowired
    private Configuration configuration;

    @Override
    public void afterPropertiesSet()  {
        // 加上这句后，可以在页面上使用shiro标签
        configuration.setSharedVariable("shiro", new ShiroTags());
        logger.debug("shiro freemarker ");
    }

}
