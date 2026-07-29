package com.balneamdp.DTO.request;

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

    @NotNull(message="El id de usuario es requerido")
    private Long userId;

    @NotNull(message = "El id de balneario es requerido")
    private Long seaSideResortId;

    @NotBlank(message = "Tipo de reserva es requerida")
    private String tipo;

    private Long filaId;

    private BigDecimal number;


}
