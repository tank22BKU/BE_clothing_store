package com.clothingstore.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Payment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    
    @Id
    @Column(name = "PaymentID", length = 100)
    private String paymentId;
    
    @Column(name = "Amount", precision = 12, scale = 2, nullable = false)
    private BigDecimal amount;
    
    @Column(name = "Date", nullable = false)
    private LocalDateTime date;
    
    @Column(name = "PaymentType", length = 50, nullable = false)
    private String paymentType;
}