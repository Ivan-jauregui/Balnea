package com.balneamdp.repository;

import com.balneamdp.models.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmenityRepository extends JpaRepository<Amenity,Long> {
    void deleteByName(String name);
    Amenity findByName(String name);
}
