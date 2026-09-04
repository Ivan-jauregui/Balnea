package com.balneamdp.mapper;

import com.balneamdp.DTO.response.PublicationResponseDto;
import com.balneamdp.DTO.request.PublicationRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {MapperSeaSideResort.class})
public interface PublicationMapper {
    Publication toEntity(PublicationRequestDto request);

    PublicationResponseDto toDto(Publication publication);
}
