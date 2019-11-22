package com.app.sqds.config;

import com.app.sqds.freemarker.DictValueEx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * freemarker函数配置
 *
 * @author ccj
 */
@Configuration
public class FreemarkerConfig {
    private Logger logger = LoggerFactory.getLogger(FreemarkerConfig.class);
    @Autowired
    private freemarker.template.Configuration configuration;

    @PostConstruct
    public void setConfigure() throws Exception {
        configuration.setSharedVariable("basePath", "/nepadmin/views/");
        configuration.setSharedVariable("dictValue", new DictValueEx());

        logger.debug("freemarker配置");
    }

}
