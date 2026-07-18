package com.balneamdp.mapper;

import com.balneamdp.DTO.SeaSideResortRequest;
import com.balneamdp.DTO.SeaSideResortResponse;
import com.balneamdp.models.Amenity;
import com.balneamdp.models.SeaSideResort;
import com.balneamdp.models.User;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-17T19:56:32-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 24.0.2 (Oracle Corporation)"
)
@Component
public class MapperSeaSideResortImpl implements MapperSeaSideResort {

    @Override
    public SeaSideResort toEntity(SeaSideResortRequest request, Set<Amenity> amenitiesEntities, User ownerEntity) {
        if ( request == null && amenitiesEntities == null && ownerEntity == null ) {
            return null;
        }

        SeaSideResort.SeaSideResortBuilder seaSideResort = SeaSideResort.builder();

        if ( request != null ) {
            seaSideResort.name( request.getName() );
            seaSideResort.description( request.getDescription() );
            seaSideResort.address( request.getAddress() );
            seaSideResort.zone( request.getZone() );
            seaSideResort.price( request.getPrice() );
        }
        Set<Amenity> set = amenitiesEntities;
        if ( set != null ) {
            seaSideResort.amenities( new LinkedHashSet<Amenity>( set ) );
        }
        seaSideResort.owner( ownerEntity );

        return seaSideResort.build();
    }

    @Override
    public SeaSideResortResponse toDto(SeaSideResort seaSideResort) {
        if ( seaSideResort == null ) {
            return null;
        }

        SeaSideResortResponse.SeaSideResortResponseBuilder seaSideResortResponse = SeaSideResortResponse.builder();

        seaSideResortResponse.id( seaSideResort.getId() );
        seaSideResortResponse.name( seaSideResort.getName() );
        seaSideResortResponse.address( seaSideResort.getAddress() );
        seaSideResortResponse.zone( seaSideResort.getZone() );
        Set<Amenity> set = seaSideResort.getAmenities();
        if ( set != null ) {
            seaSideResortResponse.amenities( new LinkedHashSet<Amenity>( set ) );
        }
        seaSideResortResponse.price( seaSideResort.getPrice() );
        seaSideResortResponse.imageUrl( seaSideResort.getImageUrl() );

        return seaSideResortResponse.build();
    }
}
