package com.berkayulke.couriertracking.courier.service.observer;

import com.berkayulke.couriertracking.courier.model.Courier;
import com.berkayulke.couriertracking.courier.service.CourierService;
import com.berkayulke.couriertracking.courier.service.request.CourierLocationUpdateRequest;
import com.berkayulke.couriertracking.logging.ApplicationLogger;
import com.berkayulke.couriertracking.store.model.Store;
import com.berkayulke.couriertracking.store.service.StoreService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class CourierStoreEntranceObserver implements CourierLocationUpdateObserver, InitializingBean {

    @Autowired
    private StoreService storeService;

    @Autowired
    private ApplicationLogger logger;
    
    @Autowired 
    private CourierService courierService;

    private static final int ENTRANCE_RADIUS = 100;
    private static final int ENTRANCE_TIME_THRESHOLD_IN_MS = 60 * 1000; // 1 minute

    @Override
    public void afterPropertiesSet() throws Exception {
        courierService.subscribe(this);
    }
    
    @Override
    public void onChange(CourierLocationUpdateRequest locationUpdate, Courier courier) {
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
    
}
