package com.balneamdp.models;

import com.balneamdp.enums.PayState;
import com.balneamdp.enums.ReservationState;
import com.balneamdp.enums.ReservationType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ReservationType type; // DIA,QUICENA,MES,TEMPORADA

    @Enumerated(EnumType.STRING)
    private ReservationState reservationState; // ACTIVA, CANCELADA, EXPIRADA

    @Enumerated(EnumType.STRING)
    private PayState payState; // PAGADO, PENDIENTE

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beach_tent_id")
    private BeachTent beachTent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="sea_sideresort_id")
    private SeaSideResort seaSideResort;

    private LocalDate startDate; // Cuando se empieza a poder usar la carpa

    private LocalDate endDate; // Cuando termina la reserva

    private LocalDateTime reservationDate; // Fecha en que se realizo la reserva

    private BigDecimal total;


    @PrePersist
    private void onCreate(){
        if(reservationState==null){ // Por defecto ponemos como activa
            reservationState=ReservationState.ACTIVA;
        }
        reservationDate= LocalDateTime.now();
    }
}
