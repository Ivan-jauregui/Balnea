package com.balneamdp.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {

    // Lee el origen desde application.properties (con http://localhost:4200 como valor por defecto)
    @Value("${app.cors.allowed-origins:http://localhost:4200}")
    private String allowedOrigins;

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // 1. Orígenes permitidos (tu app en Angular)
        config.setAllowedOrigins(List.of(allowedOrigins));

        // 2. Métodos HTTP permitidos
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));

        // 3. Cabeceras permitidas en las peticiones
        config.setAllowedHeaders(List.of("*"));

        // 4. Cabeceras que Spring expone al cliente (útil si envías JWT en el header)
        config.setExposedHeaders(List.of("Authorization"));

        // 5. Permite enviar credenciales (Cookies / Authorization headers)
        config.setAllowCredentials(true);

        // 6. Tiempo (en segundos) que el navegador guarda en caché la respuesta OPTIONS (Preflight)
        config.setMaxAge(3600L);

        // Aplicar esta configuración a todas las rutas de la API
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}