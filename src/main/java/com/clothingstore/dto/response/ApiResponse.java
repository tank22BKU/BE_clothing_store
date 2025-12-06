package com.clothingstore.dto.response;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Response wrapper chung cho tất cả API")
public class ApiResponse<T> {

    @Schema(description = "Trạng thái thành công", example = "true")
    @JsonProperty("success")
    private Boolean success;

    @Schema(description = "Thông báo kết quả", example = "Lấy danh sách khuyến mãi thành công")
    @JsonProperty("message")
    private String message;

    @Schema(description = "Dữ liệu trả về")
    @JsonProperty("data")
    private T data;

    @Schema(description = "Thời gian xử lý request (milliseconds)", example = "1702569600000")
    @JsonProperty("timestamp")
    private Long timestamp;

    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .timestamp(System.currentTimeMillis())
                .build();
    }

    public static <T> ApiResponse<T> error(String message) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .data(null)
                .timestamp(System.currentTimeMillis())
                .build();
    }
}


