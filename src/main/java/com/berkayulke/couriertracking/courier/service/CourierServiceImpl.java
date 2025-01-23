package com.berkayulke.couriertracking.courier.service;

import com.berkayulke.couriertracking.courier.model.Courier;
import com.berkayulke.couriertracking.courier.service.observer.CourierLocationUpdateObserver;
import com.berkayulke.couriertracking.courier.service.request.CourierLocationUpdateRequest;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
class CourierServiceImpl implements CourierService {
    
    @Autowired
    private CourierRepository courierRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    private final List<CourierLocationUpdateObserver> observers = new ArrayList<>();
    
    @Override
    public void subscribe(CourierLocationUpdateObserver observer) {
        observers.add(observer);
    }
    
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

    //Updates an existing courier or creates a new one 
    @Override
    public void registerLocationUpdate(String courierId, CourierLocationUpdateRequest locationUpdate) {
        Courier courier = getOrCreateCourierById(courierId);

        for (CourierLocationUpdateObserver observer : observers) {
            observer.onChange(locationUpdate, courier);
        }

        courierRepository.save(courier);
    }

    private Courier getOrCreateCourierById(String id) {
        Courier existingCourier = findById(id);
        
        if (existingCourier != null)
            return existingCourier;
        return new Courier(id);
    }
}
