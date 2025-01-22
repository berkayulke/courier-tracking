package com.berkayulke.couriertracking.store.service;

import com.berkayulke.couriertracking.store.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
    
}