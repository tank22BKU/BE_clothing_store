package com.clothingstore.controller;

import com.clothingstore.dto.request.CreateProductRequest;
import com.clothingstore.dto.request.UpdatePriceRequest;
import com.clothingstore.dto.response.CreateProductResponse;
import com.clothingstore.dto.response.ProductDetailsResponse;
import com.clothingstore.dto.response.StockStatusResponse;
import com.clothingstore.dto.response.StatusMessageResponse;
import com.clothingstore.entity.Product;
import com.clothingstore.repository.ProductRepositoryCustomImpl;
import com.clothingstore.service.AdminProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final AdminProductService adminProductService;
    private final ProductRepositoryCustomImpl productRepositoryCustomImpl;

    /**
     * Lấy danh sách tất cả Product
     * GET api/admin/products
     */
    @GetMapping
    public ResponseEntity<List<ProductDetailsResponse>> GetAllProductDetails(){
        return ResponseEntity.ok(adminProductService.getAllProductDetails());
    }


    /**
     * Thêm mới sản phẩm
     * POST /api/admin/products
     */
    @PostMapping
    public ResponseEntity<CreateProductResponse> createProduct(
            @Valid @RequestBody CreateProductRequest request) {
        try {
            String productId = adminProductService.createProduct(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(CreateProductResponse.builder()
                            .success(true)
                            .message("Product created successfully")
                            .productId(productId)
                            .build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(CreateProductResponse.builder()
                            .success(false)
                            .message(e.getMessage())
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(CreateProductResponse.builder()
                            .success(false)
                            .message("Error creating product: " + e.getMessage())
                            .build());
        }
    }

    /**
     * Cập nhật giá sản phẩm
     * PUT /api/admin/products/{productId}/price
     */
    @PutMapping("/{productId}/price")
    public ResponseEntity<StatusMessageResponse> updateProductPrice(
            @PathVariable String productId,
            @Valid @RequestBody UpdatePriceRequest request) {
        try {
            adminProductService.updateProductPrice(productId, request.getNewPrice());
            return ResponseEntity.ok(StatusMessageResponse.builder()
                    .success(true)
                    .message("Price updated successfully")
                    .build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(StatusMessageResponse.builder()
                            .success(false)
                            .message(e.getMessage())
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(StatusMessageResponse.builder()
                            .success(false)
                            .message("Error updating price: " + e.getMessage())
                            .build());
        }
    }

    /**
     * Xóa sản phẩm
     * DELETE /api/admin/products/{productId}
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<StatusMessageResponse> deleteProduct(@PathVariable String productId) {
        try {
            adminProductService.deleteProduct(productId);
            return ResponseEntity.ok(StatusMessageResponse.builder()
                    .success(true)
                    .message("Product and related data deleted successfully")
                    .build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(StatusMessageResponse.builder()
                            .success(false)
                            .message(e.getMessage())
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(StatusMessageResponse.builder()
                            .success(false)
                            .message("Error deleting product: " + e.getMessage())
                            .build());
        }
    }

    /**
     * Kiểm tra trạng thái tồn kho
     * GET /api/admin/products/{productId}/stock-status
     */
    @GetMapping("/{productId}/stock-status")
    public ResponseEntity<StockStatusResponse> getStockStatus(
            @PathVariable String productId,
            @RequestParam String warehouseId) {
        try {
            StockStatusResponse response = adminProductService.getStockStatus(productId, warehouseId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            String errorMessage = "Error checking stock status: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(StockStatusResponse.builder().message(errorMessage).build());
        }
    }
}