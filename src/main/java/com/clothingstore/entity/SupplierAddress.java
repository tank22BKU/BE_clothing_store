package com.clothingstore.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "Supplier_Address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(SupplierAddress.SupplierAddressId.class)
public class SupplierAddress {
    
    @Id
    @ManyToOne
    @JoinColumn(name = "SupplierID")
    private Supplier supplier;
    
    @Id
    @Column(name = "Address", length = 255)
    private String address;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SupplierAddressId implements Serializable {
        private String supplier;
        private String address;
    }
}