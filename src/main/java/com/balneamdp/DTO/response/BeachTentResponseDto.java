package com.balneamdp.DTO.response;

import com.balneamdp.models.Row;

import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class BeachTentResponseDto {
    private Long id;

    private Integer number;

    private BigDecimal surchargePercent;

    private Set<String> tags = new HashSet<>();

    private Boolean isActive;

    private Row row;
}
