package com.berkayulke.couriertracking.courier.controller;

import com.berkayulke.couriertracking.courier.service.CourierService;
import com.berkayulke.couriertracking.courier.service.request.CourierLocationUpdateRequest;
import com.berkayulke.couriertracking.courier.service.response.TotalTravelDistanceResponse;
import com.berkayulke.couriertracking.courier.model.Courier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/courier")
public class CourierController {

    @Autowired
    private CourierService courierService;
    
    @PostMapping("/{id}/locationUpdate")
    void locationUpdate(@PathVariable("id") String courierId, @RequestBody CourierLocationUpdateRequest locationUpdate) {
        courierService.registerLocationUpdate(courierId, locationUpdate);
    }
    
    @GetMapping("/{id}/totalTravelDistance")
    TotalTravelDistanceResponse getTotalTravelDistance(@PathVariable("id") String courierId) {
        Courier courier = courierService.findById(courierId);
        if (courier == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Courier not found");
        }
        
        return new TotalTravelDistanceResponse(courier.getTotalTravelDistance());
    }
}
