package com.balneamdp.service;

import com.balneamdp.DTO.request.RefreshTokenRequestDto;
import com.balneamdp.DTO.request.UserLoginRequestDto;
import com.balneamdp.DTO.request.UserRegisterRequestDto;
import com.balneamdp.DTO.response.AuthResponseDto;
import com.balneamdp.DTO.response.UserResponseRegisterDto;
import com.balneamdp.mapper.RegisterMapper;
import com.balneamdp.models.CustomUserDetails;
import com.balneamdp.models.RefreshToken;
import com.balneamdp.models.Role;
import com.balneamdp.models.User;
import com.balneamdp.repository.RoleRepository;
import com.balneamdp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RegisterMapper registerMapper;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public AuthResponseDto login(UserLoginRequestDto loginRequest) {

        //Validando Credenciales
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword())
        );

        //Recordamos que el usuario ya inicio sesion para su proxima peticion
        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomUserDetails userDetails=(CustomUserDetails) authentication.getPrincipal();

        //Generando token
        String accessToken = jwtService.generateToken(userDetails);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername());


        return new AuthResponseDto(
                accessToken,
                refreshToken.getToken()
        );
    }

    /* Metodo anteriormente usado para cargar el analista en el bd}*/
    public AuthResponseDto register(UserRegisterRequestDto registerRequest){

        List<Role> roles = roleRepository.findAllById(List.of(2L));


        User user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .role(new HashSet<>(roles))
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();

        CustomUserDetails userDetails = new CustomUserDetails(userRepository.save(user));

        //Generando token
        String accessToken = jwtService.generateToken(userDetails);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername());


        return new AuthResponseDto(
                accessToken,
                refreshToken.getToken()
        );
    }

    public AuthResponseDto refreshToken(RefreshTokenRequestDto request) {
        return refreshTokenService.findByToken(request.getRefreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String newAccessToken = jwtService.generateToken((UserDetails) user);
                    return new AuthResponseDto(newAccessToken, request.getRefreshToken());
                })
                .orElseThrow(() -> new RuntimeException("Refresh Token inválido o inexistente"));
    }

    // Metodo para cargar user con cualquier rol manualmente
    /*public UserResponseRegisterDto registerByAdmin(UserRegisterRequestDto registerRequest){
         List<Role> roles = roleRepository.findAllById(registerRequest.getRoleId());
        System.out.println(roles);

        if (roles.isEmpty()) {
            throw new RuntimeException("Debe seleccionar al menos un rol válido.");
        }

        User user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .role(new HashSet<>(roles))
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();


        userRepository.save(user);

        return registerMapper.toDto(registerRequest);
    }*/


}