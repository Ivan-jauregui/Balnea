package com.balneamdp.mapper;

import com.balneamdp.DTO.SeaSideResortRequest;
import com.balneamdp.DTO.SeaSideResortResponse;
import com.balneamdp.models.Amenity;
import com.balneamdp.models.SeaSideResort;
import com.balneamdp.models.User;
import com.balneamdp.repository.AmenityRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring", uses = {AmenityRepository.class})
public interface MapperSeaSideResort {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "amenities", source = "amenitiesEntities")
    @Mapping(target = "owner", source = "ownerEntity")
    SeaSideResort toEntity(SeaSideResortRequest request, Set<Amenity> amenitiesEntities, User ownerEntity   );

    SeaSideResortResponse toDto(SeaSideResort seaSideResort);

}
