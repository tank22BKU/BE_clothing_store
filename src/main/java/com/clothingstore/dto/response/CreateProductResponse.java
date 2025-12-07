package com.clothingstore.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProductResponse {
    private Boolean success;
    private String message;
    private String productId;
}
