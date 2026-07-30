package com.balneamdp.DTO.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RowRequest {

    @Positive(message = "El numero debe ser positivo")
    @NotNull(message = "El numero de fila es requerido")
    private Integer number;

    @PositiveOrZero
    @NotNull(message = "El numero de la primera carpa es requerido")
    private Integer firstBeachTent;

    @Positive
    @NotNull(message = "El numero de carpa es requerido")
    private Integer lastBeachTent;

    @NotBlank(message = "Tipo de reserva es requerida")
    private String tag;

    @NotNull(message = "El id de balneario es requerido")
    private Long seaSideResortId;
}
