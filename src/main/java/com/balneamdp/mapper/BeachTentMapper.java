package com.balneamdp.mapper;

import com.balneamdp.DTO.response.BeachTentResponseDto;
import com.balneamdp.models.BeachTent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {RowMapper.class})
public interface BeachTentMapper {
    BeachTentResponseDto toDto(BeachTent beachTent);
}
