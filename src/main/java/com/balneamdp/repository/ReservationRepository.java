package com.balneamdp.repository;

import com.balneamdp.enums.ReservationState;
import com.balneamdp.models.Reservation;
import com.balneamdp.models.SeaSideResort;
import com.balneamdp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long>, JpaSpecificationExecutor<Reservation> {
    boolean existsByBeachTent_NumberAndSeaSideResortAndReservationState(
            Integer numberBeachTent,
            SeaSideResort seaSideResort,
            ReservationState reservationState
    );

    List<Reservation> findBySeaSideResort_Id(Long seaSideResortId);

    @Query("SELECT DISTINCT r.user FROM Reservation r WHERE r.seaSideResort.id = :resortId")
    List<User> findDistinctClientsBySeaSideResortId(@Param("resortId") Long resortId);

    // Se cambió r.totalPrice por r.total y el retorno a BigDecimal
    @Query("SELECT COALESCE(SUM(r.total), 0) FROM Reservation r " +
            "WHERE MONTH(r.reservationDate) = :month " +
            "AND YEAR(r.reservationDate) = :year " +
            "AND r.seaSideResort.id = :resortId")
    BigDecimal getTotalRevenueByResortAndMonthAndYear(
            @Param("resortId") Long resortId,
            @Param("month") int month,
            @Param("year") int year
    );

    // Se cambió r.totalPrice por r.total
    @Query("SELECT MONTH(r.reservationDate), SUM(r.total) " +
            "FROM Reservation r " +
            "WHERE YEAR(r.reservationDate) = :year " +
            "AND r.seaSideResort.id = :resortId " +
            "GROUP BY MONTH(r.reservationDate)")
    List<Object[]> getAnnualMonthlyRevenueBreakdownByResort(
            @Param("resortId") Long resortId,
            @Param("year") int year
    );

    @Query("""
        SELECT COUNT(r) > 0 FROM Reservation r
        WHERE r.beachTent.id = :beachTentId
          AND r.reservationState IN :states
          AND r.startDate <= :endDate
          AND r.endDate >= :startDate
    """)
    boolean existsOverlappingReservation(
            @Param("beachTentId") Long beachTentId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("states") List<ReservationState> states
    );
}
