package com.balneamdp.DTO;

import com.balneamdp.models.Amenity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Set;

public class SeaSideResortRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    @Size(min = 50 ,max = 200)
    private String description;

    @NotBlank(message = "Adress is required")
    private String address;

    @NotNull(message = "Price is required")
    private BigDecimal price;


    @NotBlank(message = "Latitude is required")
    private Double latitude;
    @NotBlank(message = "Longitude is required")
    private Double longitude;

    @NotEmpty(message = "Amenities is required")
    private Set<Amenity> amenities;
}
