package com.balneamdp.DTO.response;

import com.balneamdp.enums.SolicitudeState;
import com.balneamdp.models.SeaSideResort;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SolicitudeResponseDto {
    private Long id;
    private String userEmail;
    private Integer rowNumber;
    private LocalDate startDate;
    private LocalDate endDate;
    private String note;
    private SolicitudeState state;
    private LocalDateTime created_at;
}
