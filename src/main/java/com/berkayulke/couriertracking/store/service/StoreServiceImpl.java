package com.berkayulke.couriertracking.store.service;

import com.berkayulke.couriertracking.helpers.DistanceHelper;
import com.berkayulke.couriertracking.store.model.Store;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private EntityManager entityManager;
    
    @Cacheable(value = "allStores")
    @Override
    public List<Store> getAll() {
        return storeRepository.findAll();
    }

    @CacheEvict(value = "allStores")
    @Override
    public void clearAllStoresCache() {
    }
    
    //TODO bunları böyle yapmak iyi mi oldu kötü mü oldu
    @Override
    public Store getClosestStoreIfWithinRange(Double latitude, Double longitude, int radius) {
        return getAll()
                .stream()
                .filter(store -> DistanceHelper.getDistance(store.getLat(), store.getLng(), latitude, longitude) < radius)
                .min((storeA, storeB) -> {
                    Double distanceA = DistanceHelper.getDistance(storeA.getLat(), storeA.getLng(), latitude, longitude);
                    Double distanceB = DistanceHelper.getDistance(storeB.getLat(), storeB.getLng(), latitude, longitude);
                    
                    return distanceA.compareTo(distanceB);
                })
                .orElse(null);
    }

}
