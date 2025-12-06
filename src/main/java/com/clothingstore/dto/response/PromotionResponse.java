package com.clothingstore.dto.response;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Thông tin chi tiết khuyến mãi")
public class PromotionResponse {

    @Schema(description = "Mã khuyến mãi", example = "PROMO001")
    @JsonProperty("promotionId")
    private String promotionId;

    @Schema(description = "Tên chương trình khuyến mãi", example = "Giảm giá mùa hè")
    @JsonProperty("name")
    private String name;

    @Schema(description = "Phần trăm giảm giá", example = "10")
    @JsonProperty("discount")
    private BigDecimal discount;

    @Schema(description = "Ngày bắt đầu khuyến mãi", example = "2024-12-01")
    @JsonProperty("startDate")
    private LocalDate startDate;

    @Schema(description = "Ngày kết thúc khuyến mãi", example = "2024-12-31")
    @JsonProperty("endDate")
    private LocalDate endDate;
}
