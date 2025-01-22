package com.berkayulke.couriertracking.courier.service;

import com.berkayulke.couriertracking.courier.model.Courier;
import com.berkayulke.couriertracking.courier.service.observer.CourierLocationUpdateObserver;
import com.berkayulke.couriertracking.courier.service.request.CourierLocationUpdateRequest;

public interface CourierService {
    void subscribe(CourierLocationUpdateObserver observer);
    Courier findById(String id);
    void registerLocationUpdate(String courierId, CourierLocationUpdateRequest locationUpdate);
}
