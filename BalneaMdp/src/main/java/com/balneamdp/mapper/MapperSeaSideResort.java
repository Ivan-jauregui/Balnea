package com.balneamdp.mapper;

import com.balneamdp.DTO.SeaSideResortRequest;
import com.balneamdp.DTO.SeaSideResortResponse;
import com.balneamdp.models.SeaSideResort;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapperSeaSideResort {
    SeaSideResort toEntity(SeaSideResortRequest request);

    SeaSideResortResponse toDto(SeaSideResort seaSideResort);
}
