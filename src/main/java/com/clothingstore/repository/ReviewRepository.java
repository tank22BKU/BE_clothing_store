package com.clothingstore.repository;

import com.clothingstore.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {

    @Query("SELECT r FROM Review r WHERE r.product.productId = :productId ORDER BY r.reviewDate DESC")
    List<Review> findByProductId(@Param("productId") String productId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.product.productId = :productId")
    Double calculateAverageRating(@Param("productId") String productId);

    @Query("SELECT COUNT(r) FROM Review r WHERE r.product.productId = :productId")
    long countByProductId(@Param("productId") String productId);
}