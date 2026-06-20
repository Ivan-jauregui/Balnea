package com.balneamdp.repository;

import com.balneamdp.models.SeaSideResort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeaSideResortRepository extends JpaRepository<SeaSideResort,Long> {
    Optional<SeaSideResort> findByName(String name);
}
