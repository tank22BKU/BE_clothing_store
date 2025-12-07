package com.clothingstore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSalesReportDto {
    private String productId;
    private String productName;
    private Integer totalQuantitySold;
    private BigDecimal totalRevenue;
    private BigDecimal averagePrice;
}