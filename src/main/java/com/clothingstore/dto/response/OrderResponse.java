package com.clothingstore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderResponse {
    private String orderId;
    private String status;
    private BigDecimal totalAmount;
    private LocalDateTime orderDate;

    private String customerId;

    private String employeeId;
    private String paymentId;
}
