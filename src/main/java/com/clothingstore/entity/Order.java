package com.clothingstore.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "`Order`")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    
    @Id
    @Column(name = "OrderID", length = 100)
    private String orderId;
    
    @Column(name = "Status", length = 30, nullable = false)
    private String status;
    
    @Column(name = "TotalAmount", precision = 12, scale = 2, nullable = false)
    private BigDecimal totalAmount = BigDecimal.ZERO;
    
    @ManyToOne
    @JoinColumn(name = "CustomerID", nullable = false)
    private Customer customer;
    
    @ManyToOne
    @JoinColumn(name = "EmployeeID", nullable = false)
    private Employee employee;
    
    @ManyToOne
    @JoinColumn(name = "PaymentID")
    private Payment payment;
    
    @Column(name = "OrderDate", nullable = false)
    private LocalDateTime orderDate;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderDetails> orderDetails;
    
    @ManyToMany
    @JoinTable(
        name = "Order_Promotion",
        joinColumns = @JoinColumn(name = "OrderID"),
        inverseJoinColumns = @JoinColumn(name = "PromotionID")
    )
    private Set<Promotion> promotions;
}