package com.berkayulke.couriertracking.courier.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "COURIER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Courier {

    @Id
    private String id;

    @Column(nullable = false)
    private Double totalTravelDistance = 0D;

    @Column()
    private Double lastLatitude;

    @Column()
    private Double lastLongitude;

    @Column()
    private Timestamp lastLocationUpdateDate;

    @Column()
    private Timestamp lastEntranceDate;

    @Column()
    private Long lastEnteredStoreId;

    public Courier(String id) {
        setId(id);
    }
    
}
