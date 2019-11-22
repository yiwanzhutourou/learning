package com.youdushufang;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class LogTest {

    private static final Logger logger = LoggerFactory.getLogger(LogTest.class);

    @Test
    void testLogback() {
        logger.info("I am your daddy");
        logger.error("Whee...", new NullPointerException());
        logger.warn("Something may happen");
        logger.info("Is debug enabled? {}", logger.isDebugEnabled());
        logger.info("Is trace enabled? {}", logger.isTraceEnabled());
    }
}
