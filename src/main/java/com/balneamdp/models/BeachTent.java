package com.balneamdp.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class BeachTent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Integer number;
    private BigDecimal surchargePercent;

    @ElementCollection
    @CollectionTable(name = "beach_tent_tags", joinColumns = @JoinColumn(name = "beach_tent_id"))
    @Column(name = "tag")
    private Set<String> tags = new HashSet<>();

    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "row_id")
    private Row row;


    @PrePersist
    protected void onCreate(){
        if(isActive==null){
            isActive=true;
        }

        if(surchargePercent==null){
            surchargePercent=BigDecimal.ZERO;
        }
    }
}
