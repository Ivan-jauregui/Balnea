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

    @Column(nullable = false)
    private BigDecimal basePrice;

    @Column(nullable = false)
    private BigDecimal seasonalPrice;

    @ManyToOne
    @JoinColumn(name = "seasideresort_id")
    private SeaSideResort seaSideResort;
}