package com.berkayulke.couriertracking.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;
    
    List<Store> getAll() {
        return storeRepository.findAll();
    }

}
