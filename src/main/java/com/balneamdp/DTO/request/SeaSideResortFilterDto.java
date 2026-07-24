package com.balneamdp.DTO.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
public class SeaSideResortFilterDto {
    private String name;
    private String zone;
    // ordenar por precio: "ASC" (menor a mayor) o "DESC" (mayor a menor)
    private String sortByPrice;
}
