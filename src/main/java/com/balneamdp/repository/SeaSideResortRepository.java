package com.balneamdp.repository;

import com.balneamdp.models.BeachTent;
import com.balneamdp.models.SeaSideResort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface SeaSideResortRepository extends JpaRepository<SeaSideResort,Long>, JpaSpecificationExecutor<SeaSideResort> {
    Optional<SeaSideResort> findByName(String name);

}
