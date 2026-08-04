package com.balneamdp.repository;

import com.balneamdp.models.BeachTent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BeachTentRepository extends JpaRepository<BeachTent,Long> {
    List<BeachTent> findByRowSeaSideResortId(Long seaSideRepositoryId);
}
