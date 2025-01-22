package com.berkayulke.couriertracking.logging.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleLoggingStrategy implements LoggingStrategy {

    private static final Logger logger = LoggerFactory.getLogger(ConsoleLoggingStrategy.class);
    
    @Override
    public String getName() {
        return "Console";
    }

    @Override
    public void info(String... messages) {
        logger.info(String.join(" ", messages));
    }

    @Override
    public void error(String... messages) {
        logger.error(String.join(" ", messages));
    }
}
