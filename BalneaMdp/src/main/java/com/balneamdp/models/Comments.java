package com.balneamdp.models;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Builder @Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ManyToOne
    @JoinColumn(name = "seaSideResort_id")
    private SeaSideResort seaSideResort;
}
