package com.berkayulke.couriertracking.logging;

import com.berkayulke.couriertracking.logging.request.ChangeLoggingStrategyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/logging")
public class LoggingController {
    
    @Autowired
    private ApplicationLogger applicationLogger;
    
    @GetMapping("/currentStrategy")
    String getCurrentStrategy() {
        return applicationLogger.getCurrentStrategy().getName();
    }
    
    @PostMapping("/currentStrategy")
    void setCurrentStrategy(@RequestBody() ChangeLoggingStrategyRequest request) {
        try {
            applicationLogger.changeStrategy(request.getName());
        } catch (ClassNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Strategy not found");
        }
    }
    
}
