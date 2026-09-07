package com.balneamdp.mapper;

import com.balneamdp.DTO.request.RowRequest;
import com.balneamdp.models.Row;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-06T21:34:28-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 25.0.2 (Oracle Corporation)"
)
@Component
public class RowMapperImpl implements RowMapper {

    @Override
    public Row toEntity(RowRequest request) {
        if ( request == null ) {
            return null;
        }

        Row.RowBuilder row = Row.builder();

        row.number( request.getNumber() );
        row.firstBeachTent( request.getFirstBeachTent() );
        row.lastBeachTent( request.getLastBeachTent() );
        row.tag( request.getTag() );

        return row.build();
    }
}
