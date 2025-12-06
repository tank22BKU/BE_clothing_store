package com.clothingstore.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "PurchaseOrder")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrder {
    
    @Id
    @Column(name = "PurchaseOrderID", length = 100)
    private String purchaseOrderId;
    
    @ManyToOne
    @JoinColumn(name = "SupplierID", nullable = false)
    private Supplier supplier;
    
    @ManyToOne
    @JoinColumn(name = "PaymentID")
    private Payment payment;
    
    @Column(name = "TotalAmount", precision = 12, scale = 2, nullable = false)
    private BigDecimal totalAmount = BigDecimal.ZERO;
    
    @ManyToOne
    @JoinColumn(name = "EmployeeID", nullable = false)
    private Employee employee;
    
    @Column(name = "OrderDate", nullable = false)
    private LocalDateTime orderDate;
    
    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL)
    private Set<PurchaseOrderDetails> purchaseOrderDetails;
}