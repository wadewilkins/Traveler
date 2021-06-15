package com.wwilkins.traveler.traveler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TravelerLogger {
    private static final Logger logger = LoggerFactory.getLogger(TravelerLogger.class);

    void log(int scv, String msg) {
        if (scv < 200 || scv >= 400) {
            logger.info("Error:" + msg);
        } else {
            logger.info("Info:" + msg);
        }
    }
}