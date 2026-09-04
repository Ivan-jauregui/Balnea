package com.balneamdp.repository;
import com.balneamdp.models.Publication;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {

    List<Publication> findBySeaSideResortId(Long seaSideResortId);
}
