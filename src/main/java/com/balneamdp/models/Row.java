package com.balneamdp.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class Row {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer number;

    @Column(nullable = false)
    private Integer firstBeachTent;

    @Column(nullable = false)
    private Integer lastBeachTent;

    @Column(nullable = false)
    private String tag;

    //Relacion con balneario correspondiente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seasideresort_id")
    private SeaSideResort seaSideResort;
}
