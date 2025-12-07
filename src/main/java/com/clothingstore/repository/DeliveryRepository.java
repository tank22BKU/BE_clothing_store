package com.clothingstore.repository;

import com.clothingstore.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, String> {

    @Query("SELECT d FROM Delivery d WHERE d.order.orderId = :orderId")
    List<Delivery> findByOrderId(@Param("orderId") String orderId);

    Optional<Delivery> findByOrder_OrderId(String orderId);
}