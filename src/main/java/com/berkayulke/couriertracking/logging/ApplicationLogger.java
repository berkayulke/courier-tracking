package com.berkayulke.couriertracking.logging;

import com.berkayulke.couriertracking.logging.strategy.ConsoleLoggingStrategy;
import com.berkayulke.couriertracking.logging.strategy.DatabaseLoggingStrategy;
import com.berkayulke.couriertracking.logging.strategy.HybridLoggingStrategy;
import com.berkayulke.couriertracking.logging.strategy.LoggingStrategy;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApplicationLogger implements InitializingBean {

    private final LoggingStrategy consoleLoggingStrategy = new ConsoleLoggingStrategy();
    
    @Autowired
    private DatabaseLoggingStrategy databaseLoggingStrategy;

    private LoggingStrategy hybridLoggingStrategy;
    
    private LoggingStrategy currentStrategy;

    @Override
    public void afterPropertiesSet() {
        hybridLoggingStrategy = new HybridLoggingStrategy(consoleLoggingStrategy, databaseLoggingStrategy);
        currentStrategy = hybridLoggingStrategy;
    }

    public LoggingStrategy getCurrentStrategy() {
        return this.currentStrategy;
    }
    
    public void changeStrategy(String newStrategy) throws ClassNotFoundException {
        if ("console".equalsIgnoreCase(newStrategy)) {
            this.currentStrategy = consoleLoggingStrategy;
        } else if ("database".equalsIgnoreCase(newStrategy)) {
            this.currentStrategy = databaseLoggingStrategy;
        } else if ("hybrid".equalsIgnoreCase(newStrategy)) {
            this.currentStrategy = hybridLoggingStrategy;
        } else {
            throw new ClassNotFoundException("Cannot find strategy named " + newStrategy);
        }
    }
    
    public void info(String... messages) {
        currentStrategy.info(messages);
    }

    public void error(String... messages) {
        currentStrategy.error(messages);
    }
}
