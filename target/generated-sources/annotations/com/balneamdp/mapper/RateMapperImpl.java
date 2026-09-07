package com.balneamdp.mapper;

import com.balneamdp.DTO.request.RateSeaSideResortRequest;
import com.balneamdp.models.RateSeaSideResort;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-07T09:56:39-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 25.0.2 (Oracle Corporation)"
)
@Component
public class RateMapperImpl implements RateMapper {

    @Override
    public RateSeaSideResort toEntity(RateSeaSideResortRequest request) {
        if ( request == null ) {
            return null;
        }

        RateSeaSideResort.RateSeaSideResortBuilder rateSeaSideResort = RateSeaSideResort.builder();

        return rateSeaSideResort.build();
    }
}
