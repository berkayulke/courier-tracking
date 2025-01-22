package com.berkayulke.couriertracking.courier.service;

import com.berkayulke.couriertracking.courier.model.Courier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourierRepository extends JpaRepository<Courier, String> {
}
