package com.balneamdp.models;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder @Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String imageUrl;
    private String imagePublicId;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "seaSideResort_id")
    private SeaSideResort seaSideResort;
}
