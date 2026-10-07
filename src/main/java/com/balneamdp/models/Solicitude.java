package com.balneamdp.models;

import com.balneamdp.enums.SolicitudeState;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter @Setter @AllArgsConstructor
@NoArgsConstructor
public class Solicitude {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne()
    @JoinColumn(name = "row_id")
    private Row row;

    @ManyToOne()
    @JoinColumn(name = "seaSideResort_id")
    private SeaSideResort seaSideResort;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private String note;

    @Enumerated(EnumType.STRING)
    private SolicitudeState state;

    private LocalDateTime created_at;

    @PrePersist
    protected void onCreate(){
        created_at= LocalDateTime.now();
        state = SolicitudeState.RECIBIDO;
    }
}
