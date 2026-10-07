package com.balneamdp.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SolicitudeState {
    RECIBIDO("Recibido"),
    PROPUESTA_ENVIADA("Propuesta Enviada"),
    ACEPTADA_CLIENTE("Aceptada por cliente"),
    SOLICITUD_CERRADA("Solicitud_Cerrada");

    private final String description;

    SolicitudeState(String description) {
        this.description = description;
    }

    @JsonValue // <- Esto le dice a Jackson que use este String al generar el JSON
    public String getDescription() {
        return description;
    }
}
