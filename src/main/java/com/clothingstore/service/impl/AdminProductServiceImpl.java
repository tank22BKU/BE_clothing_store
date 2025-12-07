package com.clothingstore.service.impl;

import com.clothingstore.dto.request.CreateProductRequest;
import com.clothingstore.dto.response.StockStatusResponse;
import com.clothingstore.entity.Category;
import com.clothingstore.entity.Product;
import com.clothingstore.repository.*;
import com.clothingstore.service.AdminProductService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminProductServiceImpl implements AdminProductService {
    private final EntityManager entityManager;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductVariantRepository variantRepository;
    private final WarehouseVariantRepository warehouseVariantRepository;
    private final ReviewRepository reviewRepository;

    @Override
    @Transactional
    public String createProduct(CreateProductRequest request) {
        log.info("Creating product with name: {}", request.getName());

        // Validate
        validateProductRequest(request);

        // Kiểm tra category tồn tại và active
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + request.getCategoryId()));

        if (category.getStatus() != Category.CategoryStatus.ACTIVE) {
            throw new IllegalArgumentException("Category is not active");
        }

        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("InsertProduct")
                .registerStoredProcedureParameter("p_Name", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_Description", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_DefaultPrice", BigDecimal.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_ProductID", String.class, ParameterMode.OUT);

        query.setParameter("p_Name", request.getName());
        query.setParameter("p_Description", request.getDescription());
        query.setParameter("p_DefaultPrice", request.getDefaultPrice());

        query.execute();

        String productId = (String) query.getOutputParameterValue("p_ProductID");
        log.info("Product ID: {}", productId);

        if (productId == null) {
            throw new IllegalStateException("InsertProduct returned NULL ProductID");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalStateException("SP created Product but cannot find it!"));

        // 4. Gán category (many-to-many)
        product.setCategories(Set.of(category));

        // 5. Gán images (elementCollection)
        if (request.getImages() != null && !request.getImages().isEmpty()) {
            product.setImages(Set.copyOf(request.getImages()));
        }

        if (product.getVariants() != null) {
            product.setVariants(new HashSet<>(product.getVariants()));
        }

        if (product.getImages() != null) {
            product.setImages(new HashSet<>(product.getImages()));
        }

        if (product.getCategories() != null) {
            product.setCategories(new HashSet<>(product.getCategories()));
        }

        if (product.getPromotions() != null) {
            product.setPromotions(new HashSet<>(product.getPromotions()));
        }

        log.info(product.getProductId() + product.getDescription() + product.getName() + product.getDefaultPrice().toString() + product.getCategories().toString() + product.getImages().toString() + product.getNumberOfRating());
        // 6. Cập nhật lại product (JPA sẽ insert bảng phụ)
        productRepository.save(product);

        log.info("Product created successfully with ID: {}", productId);
        return productId;
    }

    @Override
    @Transactional
    public void updateProductPrice(String productId, BigDecimal newPrice) {
        log.info("Updating price for product: {} to {}", productId, newPrice);

        validatePrice(newPrice);
        validateProductExists(productId);

        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("UpdateProductPrice")
                .registerStoredProcedureParameter("p_productId", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_newPrice", BigDecimal.class, ParameterMode.IN);

        query.setParameter("p_productId", productId);
        query.setParameter("p_newPrice", newPrice);

        try {
            query.execute();
            log.info("Price updated successfully for product: {}", productId);
        } catch (Exception e) {
            log.error("Error updating price for product: {}", productId, e);
            throw new RuntimeException("Failed to update product price: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteProduct(String productId) {
        log.info("Deleting product: {}", productId);

        validateProductExists(productId);

        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("DeleteProduct")
                .registerStoredProcedureParameter("p_productId", String.class, ParameterMode.IN);

        query.setParameter("p_productId", productId);

        try {
            query.execute();
            log.info("Product deleted successfully: {}", productId);
        } catch (Exception e) {
            log.error("Error deleting product: {}", productId, e);
            throw new RuntimeException("Failed to delete product: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public StockStatusResponse getStockStatus(String productId, String warehouseId) {
        log.info("Checking stock status for product: {} in warehouse: {}", productId, warehouseId);

        validateProductExists(productId);

        String sql = "SELECT GetProductStockStatus(?, ?)";
        String statusLabel = entityManager.createNativeQuery(sql)
                .setParameter(1, productId)
                .setParameter(2, warehouseId)
                .getSingleResult().toString();

        // Xác định message dựa trên status
        String message = getStockStatusMessage(statusLabel);

        return StockStatusResponse.builder()
                .productId(productId)
                .warehouseId(warehouseId)
                .statusLabel(statusLabel)
                .message(message)
                .build();
    }

    private void validateProductRequest(CreateProductRequest request) {
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }

        validatePrice(request.getDefaultPrice());
    }

    private void validatePrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }
    }

    private void validateProductExists(String productId) {
        if (!productRepository.existsById(productId)) {
            throw new IllegalArgumentException("Product not found: " + productId);
        }
    }

    private String convertImagesToJson(java.util.List<String> images) {
        if (images == null || images.isEmpty()) {
            return null;
        }
        return "[\"" + String.join("\",\"", images) + "\"]";
    }

    private String getStockStatusMessage(String statusLabel) {
        return switch (statusLabel) {
            case "Out of Stock" -> "Hết hàng - Cần nhập hàng ngay";
            case "Low Stock" -> "Cần nhập thêm hàng ngay";
            case "High Stock" -> "Tồn kho đủ";
            default -> "Trạng thái không xác định";
        };
    }
}
