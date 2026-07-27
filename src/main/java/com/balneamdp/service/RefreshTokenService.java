package com.balneamdp.service;

import com.balneamdp.exceptions.ResourseNotFoundException;
import com.balneamdp.models.RefreshToken;
import com.balneamdp.models.User;
import com.balneamdp.repository.RefreshTokenRepository;
import com.balneamdp.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    // 7 días por defecto
    private static final long  refreshTokenDurationMs=604800000;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Transactional
    public RefreshToken createRefreshToken(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourseNotFoundException("Usuario no encontrado: "));

        //En caso detener un refresh token, lo usaremos en caso de que no generamos uno nuevo
        RefreshToken refreshToken = refreshTokenRepository.findByUser(user)
                .orElseGet(()-> RefreshToken.builder().user(user).build());

        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));

        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("El Refresh Token expiró. Iniciá sesión de nuevo.");
        }
        return token;
    }
}
