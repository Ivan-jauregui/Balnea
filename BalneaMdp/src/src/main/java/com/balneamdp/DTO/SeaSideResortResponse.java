package com.balneamdp.DTO;

import com.balneamdp.models.Amenity;
import lombok.*;

import java.util.List;
import java.util.Set;

@Builder
@Getter @Setter @AllArgsConstructor  @NoArgsConstructor
public class SeaSideResortResponse {
    private String name;
    private String address;
    private Set<Amenity> amenities;
}
