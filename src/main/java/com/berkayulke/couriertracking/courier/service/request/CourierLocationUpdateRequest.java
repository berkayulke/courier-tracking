package com.berkayulke.couriertracking.courier.service.request;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class CourierLocationUpdateRequest {
    private Timestamp time;
    private Double lat;
    private Double lng;
}
