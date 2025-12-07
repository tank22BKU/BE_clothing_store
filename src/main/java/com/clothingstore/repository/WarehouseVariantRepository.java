package com.clothingstore.repository;

import com.clothingstore.entity.WarehouseVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseVariantRepository extends JpaRepository<WarehouseVariant, WarehouseVariant.WarehouseVariantId> {

    @Query("SELECT wv FROM WarehouseVariant wv WHERE wv.warehouse.warehouseId = :warehouseId AND wv.productId = :productId")
    List<WarehouseVariant> findByWarehouseIdAndProductId(@Param("warehouseId") String warehouseId,
                                                         @Param("productId") String productId);

    @Query("SELECT SUM(wv.quantityInStock) FROM WarehouseVariant wv WHERE wv.productId = :productId")
    Long getTotalStockByProductId(@Param("productId") String productId);

    @Query("SELECT wv FROM WarehouseVariant wv WHERE wv.quantityInStock <= wv.reorderPoint")
    List<WarehouseVariant> findLowStockItems();
}