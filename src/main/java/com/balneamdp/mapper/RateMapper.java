package com.balneamdp.mapper;

import com.balneamdp.DTO.request.RateSeaSideResortRequest;
import com.balneamdp.models.RateSeaSideResort;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RateMapper {
    RateSeaSideResort toEntity(RateSeaSideResortRequest request);
}
