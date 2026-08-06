package com.balneamdp.DTO.request;

import com.balneamdp.enums.PayState;
import com.balneamdp.enums.ReservationState;
import com.balneamdp.enums.ReservationType;
import com.balneamdp.models.BeachTent;
import com.balneamdp.models.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor @Builder
public class ReservationFitlerDto {
    private ReservationState reservationState;
    private PayState payState;
    private ReservationType type;
    private String searchUser;
    private Integer searchBeachTent;
}
