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
    @Column(nullable = false)
    private String title;
    @Column(columnDefinition = "LONGTEXT")
    private String description;
    @Column(nullable = false)
    private String imageUrl;
    @Column(nullable = false)
    private String imagePublicId;
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "seaSideResort_id")
    private SeaSideResort seaSideResort;

    @PrePersist
    protected void onCreate(){
        createdAt=LocalDateTime.now();
    }
}


