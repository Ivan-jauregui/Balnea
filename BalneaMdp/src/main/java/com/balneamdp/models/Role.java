package com.balneamdp.models;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
}
