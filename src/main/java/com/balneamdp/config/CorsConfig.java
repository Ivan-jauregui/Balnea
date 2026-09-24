package com.balneamdp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // 1. Lista explícita de orígenes permitidos (Local y Producción)
        config.setAllowedOrigins(List.of(
                "http://localhost:4200",
                "https://front-balnear.vercel.app"
        ));

        // 2. Métodos HTTP permitidos
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));

        // 3. Cabeceras permitidas en las peticiones
        config.setAllowedHeaders(List.of("*"));

        // 4. Cabeceras expuestas
        config.setExposedHeaders(List.of("Authorization"));

        // 5. Permite enviar credenciales (Cookies / JWT)
        config.setAllowCredentials(true);

        // 6. Tiempo de caché del Preflight
        config.setMaxAge(3600L);

        // Aplicar a todos los endpoints
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}