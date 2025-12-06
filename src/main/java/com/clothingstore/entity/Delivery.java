package com.clothingstore.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "Delivery")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Delivery {
    
    @Id
    @Column(name = "DeliveryID", length = 100)
    private String deliveryId;
    
    @Column(name = "DeliveryMethod", length = 255, nullable = false)
    private String deliveryMethod;
    
    @Column(name = "ExpectedDate", nullable = false)
    private LocalDate expectedDate;
    
    @Column(name = "DeliveryCost", precision = 12, scale = 2, nullable = false)
    private BigDecimal deliveryCost;
    
    @ManyToOne
    @JoinColumn(name = "EmployeeID", nullable = false)
    private Employee employee;
    
    @ManyToOne
    @JoinColumn(name = "OrderID", nullable = false)
    private Order order;
    
    @ManyToMany
    @JoinTable(
        name = "Delivery_Promotion",
        joinColumns = @JoinColumn(name = "DeliveryID"),
        inverseJoinColumns = @JoinColumn(name = "PromotionID")
    )
    private Set<Promotion> promotions;
}