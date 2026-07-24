package com.balneamdp.mapper;

import com.balneamdp.DTO.request.ReservationRequestDto;
import com.balneamdp.DTO.response.ReservationResponseDto;
import com.balneamdp.models.Reservation;
import com.balneamdp.models.SeaSideResort;
import com.balneamdp.models.Unit;
import com.balneamdp.models.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import javax.annotation.processing.Generated;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-24T09:10:00-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 24.0.2 (Oracle Corporation)"
)
@Component
public class ReservationMapperImpl implements ReservationMapper {

    private final DatatypeFactory datatypeFactory;

    public ReservationMapperImpl() {
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        }
        catch ( DatatypeConfigurationException ex ) {
            throw new RuntimeException( ex );
        }
    }

    @Override
    public Reservation toEntity(ReservationRequestDto request) {
        if ( request == null ) {
            return null;
        }

        Reservation.ReservationBuilder reservation = Reservation.builder();

        reservation.endDate( request.getEndDate() );

        return reservation.build();
    }

    @Override
    public ReservationResponseDto toDto(Reservation reservation) {
        if ( reservation == null ) {
            return null;
        }

        ReservationResponseDto.ReservationResponseDtoBuilder reservationResponseDto = ReservationResponseDto.builder();

        reservationResponseDto.numberUnit( reservationUnitNumber( reservation ) );
        reservationResponseDto.userEmail( reservationUserEmail( reservation ) );
        reservationResponseDto.seaSideResortName( reservationSeaSideResortName( reservation ) );
        reservationResponseDto.startDate( xmlGregorianCalendarToLocalDate( localDateTimeToXmlGregorianCalendar( reservation.getStartDate() ) ) );
        reservationResponseDto.endDate( xmlGregorianCalendarToLocalDate( localDateTimeToXmlGregorianCalendar( reservation.getEndDate() ) ) );

        return reservationResponseDto.build();
    }

    private XMLGregorianCalendar localDateTimeToXmlGregorianCalendar( LocalDateTime localDateTime ) {
        if ( localDateTime == null ) {
            return null;
        }

        return datatypeFactory.newXMLGregorianCalendar(
            localDateTime.getYear(),
            localDateTime.getMonthValue(),
            localDateTime.getDayOfMonth(),
            localDateTime.getHour(),
            localDateTime.getMinute(),
            localDateTime.getSecond(),
            localDateTime.get( ChronoField.MILLI_OF_SECOND ),
            DatatypeConstants.FIELD_UNDEFINED );
    }

    private static LocalDate xmlGregorianCalendarToLocalDate( XMLGregorianCalendar xcal ) {
        if ( xcal == null ) {
            return null;
        }

        return LocalDate.of( xcal.getYear(), xcal.getMonth(), xcal.getDay() );
    }

    private Integer reservationUnitNumber(Reservation reservation) {
        if ( reservation == null ) {
            return null;
        }
        Unit unit = reservation.getUnit();
        if ( unit == null ) {
            return null;
        }
        Integer number = unit.getNumber();
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
