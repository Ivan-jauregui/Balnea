package com.balneamdp.DTO;

import com.balneamdp.models.Amenity;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Builder
@Getter @Setter @AllArgsConstructor  @NoArgsConstructor
public class SeaSideResortResponse {
    private Long id;
    private String name;
    private String address;
    private String zone;
    private Set<Amenity> amenities;
    private BigDecimal price;
    private String imageUrl;
}
