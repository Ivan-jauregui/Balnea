package com.balneamdp.DTO;

import com.balneamdp.models.Amenity;
import com.balneamdp.models.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
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



    @NotEmpty(message = "Amenities is required")
    private Set<Long> amenities;
}
