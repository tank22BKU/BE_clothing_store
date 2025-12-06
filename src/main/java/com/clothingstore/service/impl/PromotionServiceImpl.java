package com.clothingstore.service.impl;

import com.clothingstore.dto.response.PromotionResponse;
import com.clothingstore.entity.Promotion;
import com.clothingstore.exception.ResourceNotFoundException;
import com.clothingstore.repository.PromotionRepository;
import com.clothingstore.service.PromotionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PromotionServiceImpl implements PromotionService {
    
    private final PromotionRepository promotionRepository;
    
    @Override
    public List<PromotionResponse> getAllPromotions() {
        log.info("Fetching all promotions");
        List<Promotion> promotions = promotionRepository.findAll();
        log.info("Found {} promotions", promotions.size());
        
        return promotions.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<PromotionResponse> getActivePromotions() {
        LocalDate today = LocalDate.now();
        log.info("Fetching active promotions for date: {}", today);
        
        List<Promotion> promotions = promotionRepository.findActivePromotions(today);
        log.info("Found {} active promotions", promotions.size());
        
        return promotions.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public PromotionResponse getPromotionById(String promotionId) {
        log.info("Fetching promotion with ID: {}", promotionId);
        
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> {
                    log.error("Promotion not found with ID: {}", promotionId);
                    return new ResourceNotFoundException("Không tìm thấy khuyến mãi với ID: " + promotionId);
                });
        
        log.info("Found promotion: {}", promotion.getName());
        return convertToResponse(promotion);
    }
    
    @Override
    public List<PromotionResponse> searchPromotionsByName(String name) {
        log.info("Searching promotions with name containing: {}", name);
        
        List<Promotion> promotions = promotionRepository.findByNameContainingIgnoreCase(name);
        log.info("Found {} promotions matching search criteria", promotions.size());
        
        return promotions.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Chuyển đổi entity Promotion thành PromotionResponse DTO
     * @param promotion entity cần chuyển đổi
     * @return PromotionResponse DTO
     */
    private PromotionResponse convertToResponse(Promotion promotion) {
        return PromotionResponse.builder()
                .promotionId(promotion.getPromotionId())
                .name(promotion.getName())
                .discount(promotion.getDiscount())
                .startDate(promotion.getStartDate())
                .endDate(promotion.getEndDate())
                .build();
    }
}