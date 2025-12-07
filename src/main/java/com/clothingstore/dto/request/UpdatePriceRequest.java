package com.clothingstore.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class UpdatePriceRequest {

    @NotNull(message = "New price is required")
    @Positive(message = "Price must be greater than 0")
    private BigDecimal newPrice;
}