package com.balneamdp.repository;

import com.balneamdp.models.Solicitude;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudeRepository extends JpaRepository<Solicitude,Long> {
}
