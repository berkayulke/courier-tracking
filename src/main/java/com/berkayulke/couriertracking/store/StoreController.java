package com.berkayulke.couriertracking.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
}
