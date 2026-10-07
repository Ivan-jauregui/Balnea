package com.balneamdp.DTO.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SolicitudeRequestDto {
    @NotNull(message = "El ID de usuario es requerido")
    private Long userId;
    @NotNull(message = "El ID de fila es requerido")
    private Long rowId;
    @NotNull(message = "El ID de balneario es requerido")
    private Long seaSideResortId;
    @NotNull(message = "La fecha de inicio es requerida")
    @FutureOrPresent(message = "No puedes introducir una fecha de inicio pasada")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @NotNull(message = "La fecha de fin es requerida")
    @FutureOrPresent(message = "No puedes introducir una fecha de fin pasada")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private String note;
}
