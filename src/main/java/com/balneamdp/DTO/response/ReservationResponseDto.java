package com.balneamdp.DTO.response;

import com.balneamdp.models.SeaSideResort;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationResponseDto {
    private LocalDateTime reservationDate;
    private String userEmail;
    private String seaSideResortName;
    private Long rowNumber;
    private Integer numberBeachTent;
    private BigDecimal total;

    private String initPoint;
}
