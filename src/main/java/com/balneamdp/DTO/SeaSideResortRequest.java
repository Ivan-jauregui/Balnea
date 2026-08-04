package com.balneamdp.DTO;

import com.balneamdp.DTO.request.RateSeaSideResortRequest;
import com.balneamdp.DTO.request.RowRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import java.util.Set;

@Builder
@Getter  @AllArgsConstructor  @NoArgsConstructor
public class SeaSideResortRequest {

    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    private String description;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "zone is required")
    private String zone;

    @NotNull(message = "Price is required")
    private BigDecimal price;

    @NotNull(message = "Owner ID is required")
    private Long ownerId;

    @NotNull(message = "La fecha de inicio es obligatoria")
    @FutureOrPresent(message = "No puedes introducir una fecha de apertura que ya pasó")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate; //Fecha de apertura del Balneario

    @NotNull(message = "La fecha de fin es obligatoria")
    @FutureOrPresent(message = "No puedes introducir una fecha de cierre que ya pasó")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate; //Fecha de cierre del Balneario

    @NotEmpty(message = "Amenities is required")
    private Set<Long> amenities;

    @Valid // Validación en cascada para los objetos internos
    @NotEmpty(message = "El balneario debe incluir al menos una fila de carpas")
    private Set<RowRequest> rows;

    @Valid // Validación en cascada para los objetos internos
    @NotEmpty(message = "El balneario debe incluir al menos una tarifa configurada")
    private Set<RateSeaSideResortRequest> rates;
}
