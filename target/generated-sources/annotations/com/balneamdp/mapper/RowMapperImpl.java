package com.balneamdp.mapper;

import com.balneamdp.DTO.request.RowRequest;
import com.balneamdp.models.Row;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-03T09:48:18-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 24.0.2 (Eclipse Adoptium)"
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
