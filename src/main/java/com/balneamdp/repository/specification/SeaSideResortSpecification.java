package com.balneamdp.repository.specification;

import com.balneamdp.DTO.request.SeaSideResortFilterDto;
import com.balneamdp.models.SeaSideResort;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

public class SeaSideResortSpecification {

    public static Specification<SeaSideResort> byFilter(SeaSideResortFilterDto filter){
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(filter==null){
                return null;
            }


            if (filter.getName() != null && !filter.getName().isBlank()) {

                String nameSubstring = "%" + filter.getName().toLowerCase() + "%";
                predicates.add(cb.like(cb.lower(root.get("name")), nameSubstring));
            }

            // 2. FILTRO POR ZONA
            if (filter.getZone() != null && !filter.getZone().isBlank()) {

                String zoneSubstring = "%" + filter.getZone().toLowerCase() + "%";
                predicates.add(cb.like(cb.lower(root.get("zone")), zoneSubstring));
            }

            if(predicates.isEmpty()){
                return null;
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
