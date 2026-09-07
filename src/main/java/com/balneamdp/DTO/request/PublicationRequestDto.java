package com.balneamdp.DTO.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class PublicationRequestDto {
    @NotBlank(message = "campo titulo no puede estar vacio")
    private String title;

    private String description;

    @NotNull(message = "La imagen es obligatoria")
    private MultipartFile image;
}
