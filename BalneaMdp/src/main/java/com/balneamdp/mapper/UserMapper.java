package com.balneamdp.mapper;


import com.balneamdp.DTO.request.UserRegisterRequestDto;
import com.balneamdp.DTO.response.UserResponseDto;
import com.balneamdp.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRegisterRequestDto userRequestDto);

    UserResponseDto toDto(User user);

}
