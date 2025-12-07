package com.clothingstore.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusMessageResponse {
    private Boolean success;
    private String message;
}
