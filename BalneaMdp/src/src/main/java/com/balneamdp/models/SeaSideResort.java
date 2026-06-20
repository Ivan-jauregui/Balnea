package com.balneamdp.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
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

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String address;

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

    @OneToMany(mappedBy = "seaSideResort")
    private List<Comments> comments;

    @OneToMany(mappedBy = "seaSideResort",cascade = CascadeType.ALL)
    private List<Unit> units;

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



    @Column(nullable = false)
    private LocalDateTime created_at;


    @PrePersist
    protected void onCreate(){
        created_at=LocalDateTime.now();
    }
}
