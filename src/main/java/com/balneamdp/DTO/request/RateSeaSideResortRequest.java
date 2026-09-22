package com.balneamdp.DTO.request;

import com.balneamdp.enums.ReservationType;
import com.balneamdp.models.SeaSideResort;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class RateSeaSideResortRequest {

    @NotNull(message = "El precio base de la tarifa es requerido")
    @Positive(message = "El precio base debe ser mayor a 0")
    private BigDecimal basePrice;

    @NotNull(message = "El precio de temporada es requerido")
    @Positive(message = "El precio de temporada debe ser mayor a 0")
    private BigDecimal seasonalPrice;

    private Long seaSideResortId;
}
