package com.balneamdp.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer fila;
    private Integer numero;

    private boolean enMantenimiento;

    // Relación para saber sus reservas
    @OneToMany(mappedBy = "unidad")
    private List<Reservation> reservas;
}
