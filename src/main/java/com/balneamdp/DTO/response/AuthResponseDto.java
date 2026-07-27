package com.balneamdp.DTO.response;

import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class AuthResponseDto {
    private String accessToken;
    private String refreshToken;
    private String tokenType;

    public AuthResponseDto(String accessToken, String refreshToken) {
        this(accessToken, refreshToken, "Bearer");
    }
}
