package com.balneamdp.models;

import com.balneamdp.enums.PayState;
import com.balneamdp.enums.ReservationState;
import com.balneamdp.enums.ReservationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ReservationType type;

    @Enumerated(EnumType.STRING)
    private ReservationState reservationState; // ACTIVA, CANCELADA, EXPIRADA

    @Enumerated(EnumType.STRING)
    private PayState payState; // PAGADO, PENDIENTE

    @ManyToOne
    @JoinColumn(name = "row_id")
    private Row row; // Muchas reservas pueden ser a un fila

    private Integer numberBeachTent; // Numero de carpa

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; //Muchas reservas pueden ser de un usuario en especifico

    @ManyToOne
    @JoinColumn(name="seasideresort_id")
    private SeaSideResort seaSideResort; //Muchas reservas pueden ser a un balneario

    private LocalDateTime startDate; // Cuando se empieza a poder usar la carpa

    private LocalDateTime endDate; // Cuando termina la reserva

    private LocalDateTime reservationDate; // Fecha en que se realizo la reserva


    @PrePersist
    private void onCreate(){
        if(reservationState==null){ // Por defecto ponemos como activa
            reservationState=ReservationState.ACTIVA;
        }
        reservationDate= LocalDateTime.now();
    }
}
