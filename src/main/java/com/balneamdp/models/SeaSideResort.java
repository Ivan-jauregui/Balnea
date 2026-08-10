package com.balneamdp.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    // HELPER METHODS

    public void addRow(Row row) {
        if (this.rows == null) {
            this.rows = new ArrayList<>();
        }
        this.rows.add(row);
        row.setSeaSideResort(this); // Sincroniza la clave foránea
    }

    public void removeRow(Row row) {
        if (this.rows != null) {
            this.rows.remove(row);
            row.setSeaSideResort(null);
        }
    }

    public void addRate(RateSeaSideResort rate) {
        if (this.rates == null) {
            this.rates = new ArrayList<>();
        }
        this.rates.add(rate);
        rate.setSeaSideResort(this);
    }

    public void removeRate(RateSeaSideResort rate) {
        if (this.rates != null) {
            this.rates.remove(rate);
            rate.setSeaSideResort(null);
        }
    }
}
