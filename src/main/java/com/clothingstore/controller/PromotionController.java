package com.clothingstore.controller;

import com.clothingstore.dto.response.ApiResponse;
import com.clothingstore.dto.response.PromotionResponse;
import com.clothingstore.service.PromotionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/promotions")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Promotion Management", description = "API quản lý chương trình khuyến mãi")
public class PromotionController {
    
    private final PromotionService promotionService;
    
    /**
     * Lấy danh sách tất cả các khuyến mãi
     */
    @GetMapping
    @Operation(
        summary = "Lấy danh sách tất cả khuyến mãi",
        description = "Endpoint này trả về danh sách tất cả các chương trình khuyến mãi trong hệ thống, bao gồm cả khuyến mãi đang hoạt động và đã hết hạn"
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Lấy danh sách khuyến mãi thành công",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples = @ExampleObject(
                    name = "Successful Response",
                    value = """
                    {
                      "success": true,
                      "message": "Lấy danh sách khuyến mãi thành công",
                      "data": [
                        {
                          "promotionId": "PROMO001",
                          "name": "Giảm giá mùa hè",
                          "discount": 10,
                          "startDate": "2024-12-01",
                          "endDate": "2024-12-31"
                        },
                        {
                          "promotionId": "PROMO002",
                          "name": "Khuyến mãi tết",
                          "discount": 20,
                          "startDate": "2024-01-01",
                          "endDate": "2024-02-15"
                        }
                      ],
                      "timestamp": 1702569600000
                    }
                    """
                )
            )
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "500",
            description = "Lỗi server nội bộ",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = """
                    {
                      "success": false,
                      "message": "Đã xảy ra lỗi khi lấy danh sách khuyến mãi",
                      "data": null,
                      "timestamp": 1702569600000
                    }
                    """
                )
            )
        )
    })
    public ResponseEntity<ApiResponse<List<PromotionResponse>>> getAllPromotions() {
        log.info("REST request to get all promotions");
        
        List<PromotionResponse> promotions = promotionService.getAllPromotions();
        
        return ResponseEntity.ok(
            ApiResponse.success("Lấy danh sách khuyến mãi thành công", promotions)
        );
    }
    
    /**
     * Lấy danh sách các khuyến mãi đang hoạt động
     */
    @GetMapping("/active")
    @Operation(
        summary = "Lấy danh sách khuyến mãi đang hoạt động",
        description = "Endpoint này trả về danh sách các chương trình khuyến mãi hiện đang có hiệu lực (ngày hiện tại nằm trong khoảng startDate và endDate)"
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Lấy danh sách khuyến mãi đang hoạt động thành công",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples = @ExampleObject(
                    name = "Active Promotions Response",
                    value = """
                    {
                      "success": true,
                      "message": "Lấy danh sách khuyến mãi đang hoạt động thành công",
                      "data": [
                        {
                          "promotionId": "PROMO001",
                          "name": "Giảm giá mùa hè",
                          "discount": 10,
                          "startDate": "2024-12-01",
                          "endDate": "2024-12-31"
                        }
                      ],
                      "timestamp": 1702569600000
                    }
                    """
                )
            )
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "500",
            description = "Lỗi server nội bộ"
        )
    })
    public ResponseEntity<ApiResponse<List<PromotionResponse>>> getActivePromotions() {
        log.info("REST request to get active promotions");
        
        List<PromotionResponse> promotions = promotionService.getActivePromotions();
        
        return ResponseEntity.ok(
            ApiResponse.success("Lấy danh sách khuyến mãi đang hoạt động thành công", promotions)
        );
    }
    
    /**
     * Lấy thông tin chi tiết một khuyến mãi theo ID
     */
    @GetMapping("/{promotionId}")
    @Operation(
        summary = "Lấy thông tin chi tiết khuyến mãi",
        description = "Endpoint này trả về thông tin chi tiết của một chương trình khuyến mãi dựa trên ID"
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Lấy thông tin khuyến mãi thành công",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                      "success": true,
                      "message": "Lấy thông tin khuyến mãi thành công",
                      "data": {
                        "promotionId": "PROMO001",
                        "name": "Giảm giá mùa hè",
                        "discount": 10,
                        "startDate": "2024-12-01",
                        "endDate": "2024-12-31"
                      },
                      "timestamp": 1702569600000
                    }
                    """
                )
            )
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "Không tìm thấy khuyến mãi",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = """
                    {
                      "success": false,
                      "message": "Không tìm thấy khuyến mãi với ID: PROMO999",
                      "data": null,
                      "timestamp": 1702569600000
                    }
                    """
                )
            )
        )
    })
    public ResponseEntity<ApiResponse<PromotionResponse>> getPromotionById(
            @Parameter(description = "ID của khuyến mãi", example = "PROMO001")
            @PathVariable String promotionId) {
        log.info("REST request to get promotion by ID: {}", promotionId);
        
        PromotionResponse promotion = promotionService.getPromotionById(promotionId);
        
        return ResponseEntity.ok(
            ApiResponse.success("Lấy thông tin khuyến mãi thành công", promotion)
        );
    }
    
    /**
     * Tìm kiếm khuyến mãi theo tên
     */
    @GetMapping("/search")
    @Operation(
        summary = "Tìm kiếm khuyến mãi theo tên",
        description = "Endpoint này cho phép tìm kiếm các chương trình khuyến mãi theo tên (tìm kiếm gần đúng, không phân biệt hoa thường)"
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Tìm kiếm khuyến mãi thành công",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                      "success": true,
                      "message": "Tìm kiếm khuyến mãi thành công",
                      "data": [
                        {
                          "promotionId": "PROMO001",
                          "name": "Giảm giá mùa hè",
                          "discount": 10,
                          "startDate": "2024-12-01",
                          "endDate": "2024-12-31"
                        }
                      ],
                      "timestamp": 1702569600000
                    }
                    """
                )
            )
        )
    })
    public ResponseEntity<ApiResponse<List<PromotionResponse>>> searchPromotions(
            @Parameter(description = "Từ khóa tìm kiếm trong tên khuyến mãi", example = "mùa hè")
            @RequestParam String name) {
        log.info("REST request to search promotions with name: {}", name);
        
        List<PromotionResponse> promotions = promotionService.searchPromotionsByName(name);
        
        return ResponseEntity.ok(
            ApiResponse.success("Tìm kiếm khuyến mãi thành công", promotions)
        );
    }
}