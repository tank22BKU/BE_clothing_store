package com.clothingstore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockStatusResponse {
    private String productId;
    private String warehouseId;
    private String statusLabel;
    private String message;
}
