package com.balneamdp.mapper;

import com.balneamdp.DTO.request.PublicationRequestDto;
import com.balneamdp.DTO.response.PublicationResponseDto;
import com.balneamdp.models.Publication;
import java.time.format.DateTimeFormatter;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-06T21:34:28-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 25.0.2 (Oracle Corporation)"
)
@Component
public class PublicationMapperImpl implements PublicationMapper {

    @Override
    public Publication toEntity(PublicationRequestDto request) {
        if ( request == null ) {
            return null;
        }

        Publication.PublicationBuilder publication = Publication.builder();

        publication.title( request.getTitle() );
        publication.description( request.getDescription() );
        publication.imageUrl( request.getImageUrl() );
        publication.imagePublicId( request.getImagePublicId() );

        return publication.build();
    }

    @Override
    public PublicationResponseDto toDto(Publication publication) {
        if ( publication == null ) {
            return null;
        }

        PublicationResponseDto.PublicationResponseDtoBuilder publicationResponseDto = PublicationResponseDto.builder();

        publicationResponseDto.id( publication.getId() );
        publicationResponseDto.title( publication.getTitle() );
        publicationResponseDto.description( publication.getDescription() );
        publicationResponseDto.imageUrl( publication.getImageUrl() );
        publicationResponseDto.imagePublicId( publication.getImagePublicId() );
        if ( publication.getCreatedAt() != null ) {
            publicationResponseDto.createdAt( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( publication.getCreatedAt() ) );
        }

        return publicationResponseDto.build();
    }
}
