package com.clothingstore.service;

import com.clothingstore.dto.response.PromotionResponse;
import java.time.LocalDate;
import java.util.List;

public interface PromotionService {
    
    /**
     * Lấy danh sách tất cả các khuyến mãi
     * @return danh sách PromotionResponse
     */
    List<PromotionResponse> getAllPromotions();
    
    /**
     * Lấy danh sách các khuyến mãi đang hoạt động
     * @return danh sách PromotionResponse
     */
    List<PromotionResponse> getActivePromotions();
    
    /**
     * Lấy khuyến mãi theo ID
     * @param promotionId mã khuyến mãi
     * @return PromotionResponse
     */
    PromotionResponse getPromotionById(String promotionId);
    
    /**
     * Tìm kiếm khuyến mãi theo tên
     * @param name tên khuyến mãi
     * @return danh sách PromotionResponse
     */
    List<PromotionResponse> searchPromotionsByName(String name);
}