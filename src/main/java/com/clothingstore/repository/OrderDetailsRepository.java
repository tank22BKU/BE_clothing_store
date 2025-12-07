package com.clothingstore.repository;

import com.clothingstore.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, OrderDetails.OrderDetailsId> {

    @Query("SELECT od FROM OrderDetails od WHERE od.order.orderId = :orderId")
    List<OrderDetails> findByOrderId(@Param("orderId") String orderId);

    @Query("SELECT od FROM OrderDetails od WHERE od.productId = :productId")
    List<OrderDetails> findByProductId(@Param("productId") String productId);
}