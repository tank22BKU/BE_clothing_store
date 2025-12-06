package com.clothingstore.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Order_Details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(OrderDetails.OrderDetailsId.class)
public class OrderDetails {
    
    @Id
    @ManyToOne
    @JoinColumn(name = "OrderID")
    private Order order;
    
    @Id
    @Column(name = "VariantID", length = 100)
    private String variantId;
    
    @Id
    @Column(name = "ProductID", length = 100)
    private String productId;
    
    @Id
    @Column(name = "OrderDate")
    private LocalDateTime orderDate;
    
    @Column(name = "Quantity", nullable = false)
    private Integer quantity;
    
    @Column(name = "Price", precision = 12, scale = 2, nullable = false)
    private BigDecimal price;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderDetailsId implements Serializable {
        private String order;
        private String variantId;
        private String productId;
        private LocalDateTime orderDate;
    }
}