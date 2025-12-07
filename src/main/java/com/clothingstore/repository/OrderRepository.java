package com.clothingstore.repository;

import com.clothingstore.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findByCustomer_CustomerId(String customerId);

    List<Order> findByStatus(String status);

    @Query("SELECT o FROM Order o WHERE o.customer.customerId = :customerId AND o.status = :status")
    List<Order> findByCustomerAndStatus(@Param("customerId") String customerId,
                                        @Param("status") String status);

    @Query("SELECT o FROM Order o WHERE o.orderDate BETWEEN :startDate AND :endDate")
    List<Order> findByOrderDateBetween(@Param("startDate") LocalDateTime startDate,
                                       @Param("endDate") LocalDateTime endDate);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.orderId LIKE :prefix%")
    long countByOrderIdPrefix(@Param("prefix") String prefix);
}