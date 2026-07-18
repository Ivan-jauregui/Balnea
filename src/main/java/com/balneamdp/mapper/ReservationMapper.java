package com.balneamdp.mapper;

import com.balneamdp.DTO.request.ReservationRequestDto;
import com.balneamdp.DTO.response.ReservationResponseDto;
import com.balneamdp.models.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {UserMapper.class, MapperSeaSideResort.class})
public interface ReservationMapper {
    Reservation toEntity(ReservationRequestDto request);

    @Mapping(source = " unit.number" , target="numberUnit")
    @Mapping(source = " user.email" , target="userEmail")
    @Mapping(source = "seaSideResort.name" , target="seaSideResortName")
    ReservationResponseDto toDto(Reservation reservation);
}
