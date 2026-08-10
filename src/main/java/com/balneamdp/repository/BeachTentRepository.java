package com.balneamdp.repository;

import com.balneamdp.models.BeachTent;
import com.balneamdp.models.SeaSideResort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BeachTentRepository extends JpaRepository<BeachTent,Long> {
    List<BeachTent> findByRowSeaSideResortId(Long seaSideRepositoryId);

    List<BeachTent> findByRow_SeaSideResort_Id(Long seaSideResortId);

    Optional<BeachTent> findByRowSeaSideResortAndNumber(SeaSideResort seaSideResort,Integer numberBeachTent);
}
