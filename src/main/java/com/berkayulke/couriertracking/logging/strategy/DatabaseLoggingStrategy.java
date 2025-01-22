package com.berkayulke.couriertracking.logging.strategy;

import com.berkayulke.couriertracking.logging.strategy.db.AuditLog;
import com.berkayulke.couriertracking.logging.strategy.db.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseLoggingStrategy implements LoggingStrategy {
    
    @Autowired
    private AuditLogRepository auditLogRepository;

    @Override
    public String getName() {
        return "Database";
    }

    @Override
    public void info(String... messages) {
        insert("INFO", messages);
    }

    @Override
    public void error(String... messages) {
        insert("ERROR", messages);
    }
    
    private void insert(String level, String... messages) {
        auditLogRepository.save(new AuditLog(level, String.join(" ",messages)));
    }
    
}
