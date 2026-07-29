package com.balneamdp.DTO.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RowRequest {

    @Positive(message = "El numero debe ser positivo")
    @NotNull(message = "El numero de carpa es requerido")
    private Integer number;

    @NotBlank(message = "Tipo de reserva es requerida")
    private String tag;
}
