package com.balneamdp.DTO.response;
import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class PublicationResponseDto {
    private Long id;
    private String title;
    private String description;
    private String imageUrl;
    private String createdAt;
}
