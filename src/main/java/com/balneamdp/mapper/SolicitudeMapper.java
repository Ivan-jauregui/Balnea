package com.balneamdp.mapper;

import com.balneamdp.DTO.request.ReservationRequestDto;
import com.balneamdp.DTO.request.SolicitudeRequestDto;
import com.balneamdp.DTO.response.ReservationResponseDto;
import com.balneamdp.DTO.response.SolicitudeResponseDto;
import com.balneamdp.models.Reservation;
import com.balneamdp.models.Solicitude;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {UserMapper.class,RowMapper.class})
public interface SolicitudeMapper {
    Solicitude toEntity(SolicitudeRequestDto request);

    @Mapping(source = " row.number" , target="rowNumber")
    @Mapping(source = " user.email" , target="userEmail")
    SolicitudeResponseDto toDto(Solicitude solicitude);
}
