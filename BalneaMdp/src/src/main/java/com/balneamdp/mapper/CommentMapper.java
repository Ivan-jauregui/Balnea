package com.balneamdp.mapper;

import com.balneamdp.DTO.request.CommentRequestDto;
import com.balneamdp.DTO.response.CommentResponseDto;
import com.balneamdp.models.Comments;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {MapperSeaSideResort.class})
public interface CommentMapper {

    Comments toEntity(CommentRequestDto request);

    CommentResponseDto toDto(Comments comments);
}
