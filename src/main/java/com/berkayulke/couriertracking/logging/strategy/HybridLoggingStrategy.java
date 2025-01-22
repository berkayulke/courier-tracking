package com.berkayulke.couriertracking.logging.strategy;

import java.util.Arrays;
import java.util.stream.Collectors;

public class HybridLoggingStrategy implements LoggingStrategy {
    
    private final LoggingStrategy[] strategies;
    private final String name;
    
    public HybridLoggingStrategy(LoggingStrategy... strategies){
        this.strategies = strategies;
        name = "Hybrid of " + Arrays.stream(strategies).map(LoggingStrategy::getName).collect(Collectors.joining(", "));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void info(String... messages) {
        for (LoggingStrategy strategy : strategies) {
            strategy.info(messages);
        }
    }

    @Override
    public void error(String... messages) {
        for (LoggingStrategy strategy : strategies) {
            strategy.error(messages);
        }
    }
}
