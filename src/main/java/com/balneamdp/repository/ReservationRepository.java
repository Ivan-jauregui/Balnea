package com.balneamdp.repository;

import com.balneamdp.enums.ReservationState;
import com.balneamdp.models.BeachTent;
import com.balneamdp.models.Reservation;
import com.balneamdp.models.SeaSideResort;
import com.balneamdp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    boolean existsByNumberBeachTentAndSeaSideResortAndReservationStateAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Integer numberBeachTent,
            SeaSideResort seaSideResort,
            ReservationState reservationState,
            LocalDate endDate,
            LocalDate startDate
    );

    List<Reservation> findBySeaSideResort_Id(Long seaSideResortId);
    @Query("SELECT DISTINCT r.user FROM Reservation r WHERE r.seaSideResort.id = :resortId")
    List<User> findDistinctClientsBySeaSideResortId(@Param("resortId") Long resortId);


    @Query("SELECT COALESCE(SUM(r.totalPrice), 0.0) FROM Reservation r " +
            "WHERE MONTH(r.reservationDate) = :month " +
            "AND YEAR(r.reservationDate) = :year " +
            "AND r.seaSideResort.id = :resortId")
    Double getMonthlyRevenueByResortAndMonth(
            @Param("resortId") Long resortId,
            @Param("month") int month,
            @Param("year") int year
    );
}
