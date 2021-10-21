package com.ilhak.musicstudio.helper;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Scheduled(cron = "*/30 * * * * *")
    public void test() {
        Date date = new Date();
        logger.info("log test {}", date);
    }
}
