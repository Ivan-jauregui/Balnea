package com.balneamdp.mapper;


import com.balneamdp.DTO.request.UserRegisterRequestDto;
import com.balneamdp.DTO.response.UserResponseRegisterDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RegisterMapper {
    @Mapping(target = "fullName", expression = "java(registerRequest.getFirstName() + ' ' + registerRequest.getLastName())")
    UserResponseRegisterDto toDto(UserRegisterRequestDto registerRequest);
}
