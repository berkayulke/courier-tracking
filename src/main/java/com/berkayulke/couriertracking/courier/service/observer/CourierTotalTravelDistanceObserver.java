package com.berkayulke.couriertracking.courier.service.observer;

import com.berkayulke.couriertracking.courier.model.Courier;
import com.berkayulke.couriertracking.courier.service.CourierService;
import com.berkayulke.couriertracking.courier.service.request.CourierLocationUpdateRequest;
import com.berkayulke.couriertracking.helpers.DistanceHelper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CourierTotalTravelDistanceObserver implements CourierLocationUpdateObserver, InitializingBean {

    @Autowired
    private CourierService courierService;
    
    @Override
    public void afterPropertiesSet() throws Exception {
        courierService.subscribe(this);
    }
    
    @Override
    public void onChange(CourierLocationUpdateRequest locationUpdate, Courier courier) {
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
    
}
