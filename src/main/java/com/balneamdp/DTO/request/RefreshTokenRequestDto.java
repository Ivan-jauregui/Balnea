package com.balneamdp.DTO.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data @Builder
public class RefreshTokenRequestDto {
    private String refreshToken;
}
