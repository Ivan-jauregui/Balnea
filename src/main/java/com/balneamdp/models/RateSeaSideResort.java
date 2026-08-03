package com.balneamdp.models;

import com.balneamdp.enums.ReservationType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RateSeaSideResort {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private ReservationType reservationType; // DIA, QUINCENA, MES, TEMPORADA

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "seasideresort_id")
    private SeaSideResort seaSideResort;
}