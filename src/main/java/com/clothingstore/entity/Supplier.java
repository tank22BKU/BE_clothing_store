package com.clothingstore.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Table(name = "Supplier")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Supplier {
    
    @Id
    @Column(name = "SupplierID", length = 100)
    private String supplierId;
    
    @Column(name = "Name", length = 255, nullable = false)
    private String name;
    
    @Column(name = "IsBlocked", nullable = false)
    private Boolean isBlocked = false;
    
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private Set<SupplierAddress> addresses;
    
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private Set<SupplierContact> contacts;
}