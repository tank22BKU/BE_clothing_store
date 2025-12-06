package com.clothingstore.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "Product_Variant")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(ProductVariant.ProductVariantId.class)
public class ProductVariant {
    
    @Id
    @Column(name = "VariantID", length = 100)
    private String variantId;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "ProductID")
    private Product product;
    
    @Column(name = "Size", length = 50)
    private String size;
    
    @Column(name = "Color", length = 50)
    private String color;
    
    @Column(name = "Price", precision = 12, scale = 2, nullable = false)
    private BigDecimal price;
    
    @Column(name = "BranchName", length = 255)
    private String branchName;
    
    @Column(name = "StockQuality", length = 50)
    private String stockQuality;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductVariantId implements Serializable {
        private String variantId;
        private String product;
    }
}