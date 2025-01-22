package com.berkayulke.couriertracking.logging.strategy;


public interface LoggingStrategy {
    String getName();
    void info(String... messages);
    void error(String... messages);
}
