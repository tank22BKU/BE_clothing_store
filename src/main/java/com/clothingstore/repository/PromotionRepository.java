package com.clothingstore.repository;

import com.clothingstore.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, String> {

    /**
     * Lấy tất cả khuyến mãi
     */
    List<Promotion> findAll();

    /**
     * Lấy các khuyến mãi đang hoạt động theo ngày hiện tại
     */
    @Query("SELECT p FROM Promotion p WHERE p.startDate <= :currentDate AND p.endDate >= :currentDate")
    List<Promotion> findActivePromotions(@Param("currentDate") LocalDate currentDate);

    /**
     * Tìm khuyến mãi theo khoảng thời gian
     */
    List<Promotion> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(LocalDate startDate, LocalDate endDate);

    /**
     * Tìm khuyến mãi theo tên (tìm kiếm gần đúng)
     */
    List<Promotion> findByNameContainingIgnoreCase(String name);

//    @Query("SELECT p FROM Promotion p WHERE :currentDate BETWEEN p.startDate AND p.endDate")
//    List<Promotion> findActivePromotions(@Param("currentDate") LocalDate currentDate);

    @Query("SELECT p FROM Promotion p JOIN p.products prod WHERE prod.productId = :productId AND :currentDate BETWEEN p.startDate AND p.endDate")
    List<Promotion> findActivePromotionsByProductId(@Param("productId") String productId, @Param("currentDate") LocalDate currentDate);
}
