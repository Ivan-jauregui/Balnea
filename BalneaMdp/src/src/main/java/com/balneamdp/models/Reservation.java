package com.balneamdp.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    @Enumerated(EnumType.STRING)
    private EstadoPago estadoPago; // PAGADO, SEÑADO, PENDIENTE

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
