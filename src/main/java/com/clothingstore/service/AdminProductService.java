package com.clothingstore.service;

import com.clothingstore.dto.request.CreateProductRequest;
import com.clothingstore.dto.response.ProductDetailsResponse;
import com.clothingstore.dto.response.StockStatusResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public interface AdminProductService {
    String createProduct(@Valid CreateProductRequest request);
    void updateProductPrice(String productId, @NotNull(message = "New price is required") @Positive(message = "Price must be greater than 0") BigDecimal newPrice);
    void deleteProduct(String productId);
    StockStatusResponse getStockStatus(String productId, String warehouseId);
    List<ProductDetailsResponse> getAllProductDetails();
}
