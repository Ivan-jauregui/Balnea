package com.balneamdp.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Builder @Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class SeaSideResort {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String name;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    @Column(nullable = false)
    private String address;


    private String zone;

    @Column(nullable = false)
    private BigDecimal price;


    // Coordenadas para el mapa
    private Double latitude;
    private Double longitude;

    @ManyToMany
    @JoinTable(
            name = "seasideresort_services",
            joinColumns = @JoinColumn(name = "seasideresort_id"),
            inverseJoinColumns = @JoinColumn(name = "amenities_id")
    )
    private Set<Amenity> amenities;

    @OneToMany(mappedBy = "seaSideResort",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comments> comments;

    @OneToMany(mappedBy = "seaSideResort",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Row> rows;

    @OneToMany(mappedBy = "seaSideResort", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RateSeaSideResort> rates;

    @ManyToMany
    @JoinTable(
            name = "seasideresort_clients",
            joinColumns = @JoinColumn(name = "seasideresort_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> clients;

    @OneToOne()
    @JoinColumn(name = "owner_id",referencedColumnName = "id")
    private User owner;

    private String imageUrl;
    private String imagePublicId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate; //Fecha de apertura del Balneario

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate; //Fecha de cierre del Balneario

    @Column(nullable = false)
    private LocalDateTime created_at;


    @PrePersist
    protected void onCreate(){
        created_at=LocalDateTime.now();
    }
}
