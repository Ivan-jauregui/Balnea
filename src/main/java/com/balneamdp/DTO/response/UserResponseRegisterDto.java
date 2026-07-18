package com.balneamdp.DTO.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseRegisterDto {
    private String fullName;
    private String email;
}
