package com.balneamdp.repository;

import com.balneamdp.models.Row;
import com.balneamdp.models.SeaSideResort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RowRepository extends JpaRepository<Row,Long> {
    Optional<Row> findByNumberAndSeaSideResort(Integer number, SeaSideResort seaSideResort);
}
