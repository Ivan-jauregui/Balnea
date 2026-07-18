package com.balneamdp.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Entity
@Builder
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "unit_id",referencedColumnName = "id")
    private Unit unit;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="seasideresort_id")
    private SeaSideResort seaSideResort;

    @PrePersist
    private void onCreate(){
        startDate= LocalDateTime.now();
    }
}
