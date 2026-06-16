package com.balneamdp.DTO.request;
import jakarta.validation.constraints.*;
import lombok.*;


@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class UserLoginRequestDto {
    @NotBlank(message="El email es requerido")
    @Email(message = "Error en el formato del email")
    private String email;
    @NotBlank(message = "La contraseña es requida")
    private String password;
}
