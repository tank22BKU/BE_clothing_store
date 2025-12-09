package com.clothingstore.repository;

import com.clothingstore.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, ProductVariant.ProductVariantId> {

    @Query("SELECT pv FROM ProductVariant pv WHERE pv.product.productId = :productId")
    List<ProductVariant> findByProductId(@Param("productId") String productId);

    @Query("SELECT pv FROM ProductVariant pv WHERE pv.variantId = :variantId AND pv.product.productId = :productId")
    ProductVariant findByVariantIdAndProductId(@Param("variantId") String variantId,
                                               @Param("productId") String productId);
    @Query(value = "SELECT VariantID FROM product_variant ORDER BY VariantID DESC LIMIT 1", nativeQuery = true)
    String findMaxVariantId();
}