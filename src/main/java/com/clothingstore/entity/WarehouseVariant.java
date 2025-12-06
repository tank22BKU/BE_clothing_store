package com.clothingstore.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "Warehouse_Variant")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(WarehouseVariant.WarehouseVariantId.class)
public class WarehouseVariant {
    
    @Id
    @ManyToOne
    @JoinColumn(name = "WarehouseID")
    private Warehouse warehouse;
    
    @Id
    @Column(name = "VariantID", length = 100)
    private String variantId;
    
    @Id
    @Column(name = "ProductID", length = 100)
    private String productId;
    
    @Column(name = "ReorderPoint", nullable = false)
    private Integer reorderPoint;
    
    @Column(name = "QuantityInStock", nullable = false)
    private Integer quantityInStock;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WarehouseVariantId implements Serializable {
        private String warehouse;
        private String variantId;
        private String productId;
    }
}