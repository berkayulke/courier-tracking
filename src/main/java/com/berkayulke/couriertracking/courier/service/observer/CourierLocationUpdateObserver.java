package com.berkayulke.couriertracking.courier.service.observer;

import com.berkayulke.couriertracking.courier.model.Courier;
import com.berkayulke.couriertracking.courier.service.request.CourierLocationUpdateRequest;

public interface CourierLocationUpdateObserver {
    void onChange(CourierLocationUpdateRequest locationUpdate, Courier courier);
}
