package com.berkayulke.couriertracking.store.service;

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
    
    @Override
    public Store getClosestStoreIfWithinRange(Double latitude, Double longitude, double radiusInMeters) {
        //Use Haversina formula to sort stores https://en.wikipedia.org/wiki/Haversine_formula
        String sql = """
                SELECT STORES.*, (
                    6371 * ACOS(
                        COS(RADIANS(:latitude)) * COS(RADIANS(lat)) *
                        COS(RADIANS(lng) - RADIANS(:longitude)) + 
                        SIN(RADIANS(:latitude)) * SIN(RADIANS(lat))
                    )
                ) AS distance
                FROM STORES
                WHERE distance < :radius
                ORDER BY distance
                """;

        List<Store> stores = entityManager.createNativeQuery(sql, Store.class)
                .setParameter("latitude", latitude)
                .setParameter("longitude", longitude)
                .setParameter("radius", radiusInMeters / 1000)
                .setMaxResults(1)
                .getResultList();
        
        return stores.size() > 0 ? stores.get(0) : null;
    }

}
