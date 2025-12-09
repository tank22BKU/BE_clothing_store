package com.clothingstore.repository;

import com.clothingstore.dto.response.ProductDetailsResponse;
import com.clothingstore.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.images LEFT JOIN FETCH p.categories WHERE p.productId = :productId")
    Optional<Product> findByIdWithDetails(@Param("productId") String productId);

    @Query("SELECT COUNT(p) FROM Product p WHERE p.productId LIKE :prefix%")
    long countByProductIdPrefix(@Param("prefix") String prefix);
}