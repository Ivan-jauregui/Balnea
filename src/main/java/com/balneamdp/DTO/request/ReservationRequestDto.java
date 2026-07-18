package com.balneamdp.DTO.request;

import com.balneamdp.models.SeaSideResort;
import com.balneamdp.models.Unit;
import com.balneamdp.models.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class ReservationRequestDto {
    @Future(message = "La fecha final no puede ser presente")
    @NotNull(message = "La fecha es obligatoria y no puede estar vacía")
    private LocalDateTime endDate;

    @NotNull(message="El id de usuario es requerido")
    private Long userId;

    @NotNull(message = "El id de balneario es requerido")
    private Long seaSideResortId;
}
