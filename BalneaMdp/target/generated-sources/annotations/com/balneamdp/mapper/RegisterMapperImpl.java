package com.balneamdp.mapper;

import com.balneamdp.DTO.request.UserRegisterRequestDto;
import com.balneamdp.DTO.response.UserResponseRegisterDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-16T12:35:39-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 24.0.2 (Oracle Corporation)"
)
@Component
public class RegisterMapperImpl implements RegisterMapper {

    @Override
    public UserResponseRegisterDto toDto(UserRegisterRequestDto registerRequest) {
        if ( registerRequest == null ) {
            return null;
        }

        UserResponseRegisterDto.UserResponseRegisterDtoBuilder userResponseRegisterDto = UserResponseRegisterDto.builder();

        userResponseRegisterDto.email( registerRequest.getEmail() );

        userResponseRegisterDto.fullName( registerRequest.getFirstName() + ' ' + registerRequest.getLastName() );

        return userResponseRegisterDto.build();
    }
}
