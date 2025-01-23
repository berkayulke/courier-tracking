package com.berkayulke.couriertracking.store.service;

import com.berkayulke.couriertracking.store.model.Store;

import java.util.List;

public interface StoreService {
    List<Store> getAll();
    void clearAllStoresCache();
    Store getClosestStoreIfWithinRange(Double latitude, Double longitude, double radius);
}
