package com.app.app.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DemoTask {
    private Logger logger = LoggerFactory.getLogger(DemoTask.class);

    @Scheduled(fixedRate = 60000)
    public void reportCurrentTime() {
        logger.debug("framework 2020");
    }
}
