package com.balneamdp.models;

import com.balneamdp.enums.PayState;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder @Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer number;

    private boolean inMaintenance;

    @Enumerated(EnumType.STRING)
    private PayState payState; // PAGADO, SEÑADO, PENDIENTE

    @ManyToOne
    @JoinColumn(name="seasideresort_id")
    @JsonIgnore
    private SeaSideResort seaSideResort;

}
