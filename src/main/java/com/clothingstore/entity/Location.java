package com.clothingstore.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "Location")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(Location.LocationId.class)
public class Location {
    
    @Id
    @Column(name = "Location", length = 100)
    private String location;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "WarehouseID")
    private Warehouse warehouse;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LocationId implements Serializable {
        private String location;
        private String warehouse;
    }
}