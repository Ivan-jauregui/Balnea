package com.balneamdp.mapper;

import com.balneamdp.DTO.SeaSideResortRequest;
import com.balneamdp.DTO.SeaSideResortResponse;
import com.balneamdp.models.Amenity;
import com.balneamdp.models.SeaSideResort;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-12T21:36:45-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 24.0.2 (Oracle Corporation)"
)
@Component
public class MapperSeaSideResortImpl implements MapperSeaSideResort {

    @Override
    public SeaSideResort toEntity(SeaSideResortRequest request) {
        if ( request == null ) {
            return null;
        }

        SeaSideResort.SeaSideResortBuilder seaSideResort = SeaSideResort.builder();

        seaSideResort.id( request.getId() );
        seaSideResort.name( request.getName() );
        seaSideResort.description( request.getDescription() );
        seaSideResort.address( request.getAddress() );
        seaSideResort.price( request.getPrice() );
        seaSideResort.latitude( request.getLatitude() );
        seaSideResort.longitude( request.getLongitude() );
        Set<Amenity> set = request.getAmenities();
        if ( set != null ) {
            seaSideResort.amenities( new LinkedHashSet<Amenity>( set ) );
        }

        return seaSideResort.build();
    }

    @Override
    public SeaSideResortResponse toDto(SeaSideResort seaSideResort) {
        if ( seaSideResort == null ) {
            return null;
        }

        SeaSideResortResponse.SeaSideResortResponseBuilder seaSideResortResponse = SeaSideResortResponse.builder();

        seaSideResortResponse.name( seaSideResort.getName() );
        seaSideResortResponse.address( seaSideResort.getAddress() );
        Set<Amenity> set = seaSideResort.getAmenities();
        if ( set != null ) {
            seaSideResortResponse.amenities( new LinkedHashSet<Amenity>( set ) );
        }

        return seaSideResortResponse.build();
    }
}
