package com.clothingstore.repository;

import com.clothingstore.entity.SubmitReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmitReviewRepository extends JpaRepository<SubmitReview, SubmitReview.SubmitReviewId> {

    @Query("SELECT sr FROM SubmitReview sr WHERE sr.customer.customerId = :customerId")
    List<SubmitReview> findByCustomerId(@Param("customerId") String customerId);

    @Query("SELECT sr FROM SubmitReview sr WHERE sr.order.orderId = :orderId")
    List<SubmitReview> findByOrderId(@Param("orderId") String orderId);
}