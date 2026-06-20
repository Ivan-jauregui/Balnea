package com.balneamdp.DTO.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequestDto {
    @NotBlank(message = "Texto es requerido")
    private String text;
    @NotNull(message = "id es balneario es requerido")
    private Long seaSideResortId;
}
