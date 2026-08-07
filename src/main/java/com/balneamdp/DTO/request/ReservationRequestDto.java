package com.balneamdp.DTO.request;

import com.balneamdp.enums.ReservationType;
import com.balneamdp.models.SeaSideResort;
import com.balneamdp.models.Unit;
import com.balneamdp.models.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class ReservationRequestDto {

    // User que reservara
    @NotNull(message="El id de usuario es requerido")
    private Long userId;

    // Balneario objetivo
    @NotNull(message = "El id de balneario es requerido")
    private Long seaSideResortId;

    // Tipo de reserva que se querra efectuar
    @NotBlank(message = "Tipo de reserva es requerida")
    private ReservationType type;

    // La fila que se querra seleccionar
    @NotNull(message = "El numero de fila es requerido")
    private Integer numberRow;

    // La carpa que se eligirá
    @NotNull(message = "El numero de carpa es requerido")
    private Integer numberBeachTent;

    @NotNull(message = "La fecha de inicio es requerida")
    private LocalDate startDate;
}
