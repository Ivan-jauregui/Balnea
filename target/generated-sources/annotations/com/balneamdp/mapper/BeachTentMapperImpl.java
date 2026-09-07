package com.balneamdp.mapper;

import com.balneamdp.DTO.response.BeachTentResponseDto;
import com.balneamdp.models.BeachTent;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-06T21:34:28-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 25.0.2 (Oracle Corporation)"
)
@Component
public class BeachTentMapperImpl implements BeachTentMapper {

    @Override
    public BeachTentResponseDto toDto(BeachTent beachTent) {
        if ( beachTent == null ) {
            return null;
        }

        BeachTentResponseDto.BeachTentResponseDtoBuilder beachTentResponseDto = BeachTentResponseDto.builder();

        beachTentResponseDto.id( beachTent.getId() );
        beachTentResponseDto.number( beachTent.getNumber() );
        beachTentResponseDto.surchargePercent( beachTent.getSurchargePercent() );
        Set<String> set = beachTent.getTags();
        if ( set != null ) {
            beachTentResponseDto.tags( new LinkedHashSet<String>( set ) );
        }
        beachTentResponseDto.isActive( beachTent.getIsActive() );
        beachTentResponseDto.row( beachTent.getRow() );

        return beachTentResponseDto.build();
    }
}
