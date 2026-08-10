package com.balneamdp.DTO.request;

import com.balneamdp.enums.ReservationType;
import com.balneamdp.models.SeaSideResort;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class RateSeaSideResortRequest {

    @NotNull(message = "El tipo de reserva es requerido")
    private ReservationType reservationType;

    @Positive(message = "El precio de la tarifa debe ser mayor a 0")
    @NotNull(message = "El precio de la tarifa es requerido")
    private BigDecimal price;

    private Long seaSideResortId;
}
