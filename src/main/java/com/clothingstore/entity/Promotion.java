package com.clothingstore.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "Promotion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Promotion {
    
    @Id
    @Column(name = "PromotionID", length = 100)
    private String promotionId;
    
    @Column(name = "Name", length = 255, nullable = false)
    private String name;
    
    @Column(name = "Discount", precision = 5, scale = 2, nullable = false)
    private BigDecimal discount;
    
    @Column(name = "StartDate", nullable = false)
    private LocalDate startDate;
    
    @Column(name = "EndDate", nullable = false)
    private LocalDate endDate;
    
    @ManyToMany
    @JoinTable(
        name = "Promotion_Product",
        joinColumns = @JoinColumn(name = "PromotionID"),
        inverseJoinColumns = @JoinColumn(name = "ProductID")
    )
    private Set<Product> products;
    
    @ManyToMany(mappedBy = "promotions")
    private Set<Delivery> deliveries;
    
    @ManyToMany(mappedBy = "promotions")
    private Set<Order> orders;
}