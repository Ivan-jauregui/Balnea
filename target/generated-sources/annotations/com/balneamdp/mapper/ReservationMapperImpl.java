package com.balneamdp.mapper;

import com.balneamdp.DTO.request.ReservationRequestDto;
import com.balneamdp.DTO.response.ReservationResponseDto;
import com.balneamdp.models.Reservation;
import com.balneamdp.models.Row;
import com.balneamdp.models.SeaSideResort;
import com.balneamdp.models.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-06T21:34:28-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 25.0.2 (Oracle Corporation)"
)
@Component
public class ReservationMapperImpl implements ReservationMapper {

    @Override
    public Reservation toEntity(ReservationRequestDto request) {
        if ( request == null ) {
            return null;
        }

        Reservation.ReservationBuilder reservation = Reservation.builder();

        reservation.startDate( request.getStartDate() );
        reservation.endDate( request.getEndDate() );

        return reservation.build();
    }

    @Override
    public ReservationResponseDto toDto(Reservation reservation) {
        if ( reservation == null ) {
            return null;
        }

        ReservationResponseDto.ReservationResponseDtoBuilder reservationResponseDto = ReservationResponseDto.builder();

        Integer number = reservationRowNumber( reservation );
        if ( number != null ) {
            reservationResponseDto.rowNumber( number.longValue() );
        }
        reservationResponseDto.userEmail( reservationUserEmail( reservation ) );
        reservationResponseDto.seaSideResortName( reservationSeaSideResortName( reservation ) );
        reservationResponseDto.reservationDate( reservation.getReservationDate() );

        return reservationResponseDto.build();
    }

    private Integer reservationRowNumber(Reservation reservation) {
        if ( reservation == null ) {
            return null;
        }
        Row row = reservation.getRow();
        if ( row == null ) {
            return null;
        }
        Integer number = row.getNumber();
        if ( number == null ) {
            return null;
        }
        return number;
    }

    private String reservationUserEmail(Reservation reservation) {
        if ( reservation == null ) {
            return null;
        }
        User user = reservation.getUser();
        if ( user == null ) {
            return null;
        }
        String email = user.getEmail();
        if ( email == null ) {
            return null;
        }
        return email;
    }

    private String reservationSeaSideResortName(Reservation reservation) {
        if ( reservation == null ) {
            return null;
        }
        SeaSideResort seaSideResort = reservation.getSeaSideResort();
        if ( seaSideResort == null ) {
            return null;
        }
        String name = seaSideResort.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
