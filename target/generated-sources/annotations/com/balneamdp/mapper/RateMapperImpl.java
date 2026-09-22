package com.balneamdp.mapper;

import com.balneamdp.DTO.request.RateSeaSideResortRequest;
import com.balneamdp.models.RateSeaSideResort;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-22T09:46:15-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 25.0.4.1 (Microsoft)"
)
@Component
public class RateMapperImpl implements RateMapper {

    @Override
    public RateSeaSideResort toEntity(RateSeaSideResortRequest request) {
        if ( request == null ) {
            return null;
        }

        RateSeaSideResort.RateSeaSideResortBuilder rateSeaSideResort = RateSeaSideResort.builder();

        rateSeaSideResort.basePrice( request.getBasePrice() );
        rateSeaSideResort.seasonalPrice( request.getSeasonalPrice() );

        return rateSeaSideResort.build();
    }
}
