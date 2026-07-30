package com.balneamdp.repository;

import com.balneamdp.enums.ReservationState;
import com.balneamdp.models.Reservation;
import com.balneamdp.models.SeaSideResort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    boolean existsByNumberBeachTentAndSeaSideResortAndReservationStateAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Integer numberBeachTent,
            SeaSideResort seaSideResort,
            ReservationState reservationState,
            LocalDateTime endDate,
            LocalDateTime startDate
    );
}
