package com.balneamdp.DTO.response;

import com.balneamdp.models.SeaSideResort;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationResponseDto {
    private Integer numberUnit;
    private LocalDate startDate;
    private LocalDate endDate;
    private String userEmail;
    private String seaSideResortName;
}
