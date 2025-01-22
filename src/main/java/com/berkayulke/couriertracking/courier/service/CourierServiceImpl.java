package com.berkayulke.couriertracking.courier.service;

import com.berkayulke.couriertracking.courier.model.Courier;
import com.berkayulke.couriertracking.courier.service.request.CourierLocationUpdateRequest;
import com.berkayulke.couriertracking.helpers.DistanceHelper;
import com.berkayulke.couriertracking.logging.ApplicationLogger;
import com.berkayulke.couriertracking.store.model.Store;
import com.berkayulke.couriertracking.store.service.StoreService;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
class CourierServiceImpl implements CourierService {
    
    @Autowired
    private CourierRepository courierRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private StoreService storeService;
    
    private static final int ENTRANCE_RADIUS = 100;
    private static final int ENTRANCE_TIME_THRESHOLD_IN_MS = 60 * 1000; // 1 minute
    
    @Autowired
    private ApplicationLogger logger;

    @Override
    public Courier findById(String id) {
        List<Courier> courierList = entityManager.createQuery("from Courier where id=:id", Courier.class)
                .setParameter("id", id)
                .setMaxResults(1)
                .getResultList();
        if (courierList.size() > 0)
            return courierList.get(0);
        return null;
    }

    //TODO total distance için startDate endDate de alıp ona göre sorgulama
    //Updates an existing courier or creates a new one 
    @Override
    public void registerLocationUpdate(String courierId, CourierLocationUpdateRequest locationUpdate) {
        Courier courier = getOrCreateCourierById(courierId);

        updateEntranceFields(locationUpdate, courier);
        updateTotalTravelDistanceFields(locationUpdate, courier);

        courierRepository.save(courier);
    }

    private void updateEntranceFields(CourierLocationUpdateRequest locationUpdate, Courier courier) {
        if (courier == null)
            return;

        Store closestStore = storeService.getClosestStoreIfWithinRange(locationUpdate.getLat(), locationUpdate.getLng(), ENTRANCE_RADIUS);
        if (closestStore == null)
            return;
        
        boolean isFirstEntrance = courier.getLastEntranceDate() == null || courier.getLastEnteredStoreId() == null;
        boolean isEnteredToNewStore = !closestStore.getId().equals(courier.getLastEnteredStoreId()); 
        boolean isNewEntranceUnderThreshold = !isFirstEntrance && courier.getLastEntranceDate().after(new Timestamp(locationUpdate.getTime().getTime() - ENTRANCE_TIME_THRESHOLD_IN_MS));
        if (isEnteredToNewStore || isFirstEntrance || isNewEntranceUnderThreshold) {
            logger.info("Courier " + courier.getId() + " has entered the radius of " + closestStore.getName());
        }

        courier.setLastEntranceDate(locationUpdate.getTime());
        courier.setLastEnteredStoreId(closestStore.getId());
    }

    private static void updateTotalTravelDistanceFields(CourierLocationUpdateRequest locationUpdate, Courier courier) {
        if (courier == null || locationUpdate == null)
            return;
        
        if (courier.getLastLongitude() != null && courier.getLastLatitude() != null) {
            Double distanceTraveled = DistanceHelper.getDistance(locationUpdate.getLat(), locationUpdate.getLng(), courier.getLastLatitude(), courier.getLastLongitude());
            
            if (courier.getTotalTravelDistance() != null)
                courier.setTotalTravelDistance(courier.getTotalTravelDistance() + distanceTraveled);
            else
                courier.setTotalTravelDistance(distanceTraveled);
        }

        courier.setLastLatitude(locationUpdate.getLat());
        courier.setLastLongitude(locationUpdate.getLng());
        courier.setLastLocationUpdateDate(locationUpdate.getTime());
    }

    private Courier getOrCreateCourierById(String id) {
        Courier existingCourier = findById(id);
        
        if (existingCourier != null)
            return existingCourier;
        return new Courier(id);
    }
}
