package com.balneamdp.repository;

import com.balneamdp.enums.ReservationType;
import com.balneamdp.models.RateSeaSideResort;
import com.balneamdp.models.SeaSideResort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RateSeaSideResortRepository extends JpaRepository<RateSeaSideResort, Long> {

    Optional<RateSeaSideResort> findByReservationTypeAndSeaSideResort(ReservationType reservationType, SeaSideResort seaSideResort);

    RateSeaSideResort findBySeaSideResortId(Long id);
}