package com.berkayulke.couriertracking.store.controller;

import com.berkayulke.couriertracking.store.model.Store;
import com.berkayulke.couriertracking.store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stores")
public class StoreController {

    @Autowired
    private StoreService storeService;
    
    @GetMapping
    List<Store> getAllStores() {
        return storeService.getAll();
    }
    
    @PostMapping("/clearCache")
    void clearStoreCache() {
        storeService.clearAllStoresCache();
    }
    
}
