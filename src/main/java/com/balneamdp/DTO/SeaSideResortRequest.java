package com.balneamdp.DTO;

import com.balneamdp.models.Amenity;
import com.balneamdp.models.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    private LocalDateTime startDate; //Fecha de apertura del Balneario

    @NotNull(message = "La fecha de fin es obligatoria")
    @FutureOrPresent(message = "No puedes introducir una fecha de cierre que ya pasó")
    private LocalDateTime andDate; //Fecha de cierre del Balneario

    @NotEmpty(message = "Amenities is required")
    private Set<Long> amenities;
}
