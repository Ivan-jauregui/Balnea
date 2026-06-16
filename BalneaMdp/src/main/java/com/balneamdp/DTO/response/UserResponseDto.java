package com.balneamdp.DTO.response;

import com.balneamdp.models.Role;

import java.util.Set;

public class UserResponseDto {
    private String firstName;
    private String lastName;
    private Set<Role> role;
    private String email;
}
