package com.balneamdp.repository;
import com.balneamdp.models.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {

    List<Publication> findBySeaSideResortId(Long seaSideResortId);
}
