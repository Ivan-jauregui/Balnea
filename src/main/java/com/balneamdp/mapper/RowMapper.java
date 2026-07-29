package com.balneamdp.mapper;

import com.balneamdp.DTO.request.RowRequest;
import com.balneamdp.models.Row;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RowMapper {
    Row toEntity(RowRequest request);
}
