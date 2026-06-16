package com.balneamdp.mapper;

import com.balneamdp.DTO.request.UserRegisterRequestDto;
import com.balneamdp.DTO.response.UserResponseDto;
import com.balneamdp.models.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-16T12:35:39-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 24.0.2 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserRegisterRequestDto userRequestDto) {
        if ( userRequestDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.firstName( userRequestDto.getFirstName() );
        user.lastName( userRequestDto.getLastName() );
        user.email( userRequestDto.getEmail() );
        user.password( userRequestDto.getPassword() );

        return user.build();
    }

    @Override
    public UserResponseDto toDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponseDto userResponseDto = new UserResponseDto();

        return userResponseDto;
    }
}
