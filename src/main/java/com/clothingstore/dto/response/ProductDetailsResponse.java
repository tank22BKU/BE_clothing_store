package com.clothingstore.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
public class ProductDetailsResponse {
    private String productId;
    private String imageURL;
    private String productName;
    private String variantSize;
    private String variantColor;
    private BigDecimal variantPrice;
    private String productDescription;

    @QueryProjection
    public ProductDetailsResponse(String productId,String imageURL, String productName, String variantSize, String variantColor, BigDecimal variantPrice, String productDescription) {
        this.productId = productId;
        this.imageURL = imageURL;
        this.productName = productName;
        this.variantSize = variantSize;
        this.variantColor = variantColor;
        this.variantPrice = variantPrice;
        this.productDescription = productDescription;
    }
}

