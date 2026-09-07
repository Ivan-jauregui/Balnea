package com.balneamdp.DTO.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class PublicationRequestDto {
    @NotBlank(message = "campo titulo no puede estar vacio")
    private String title;

    private String description;

    @NotBlank(message = "Debes ingresar una imagen")
    private String imageUrl;

    @NotBlank(message = "Debes ingresar una imagen")
    private String imagePublicId;

    @NotNull(message = "id es balneario es requerido")
    private Long seaSideResortId;
}
