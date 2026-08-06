package com.balneamdp.repository.specification;

import com.balneamdp.DTO.request.ReservationFitlerDto;
import com.balneamdp.models.Reservation;
import com.balneamdp.models.SeaSideResort;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate; // Asegúrate de importar de jakarta

import java.util.ArrayList;
import java.util.List;

public class ReservationSpecification {

    public static Specification<Reservation> byFilter(ReservationFitlerDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter == null) {
                return null;
            }

            if (filter.getReservationState() != null) {
                predicates.add(cb.equal(root.get("reservationState"), filter.getReservationState()));
            }

            if (filter.getPayState() != null) {
                predicates.add(cb.equal(root.get("payState"), filter.getPayState()));
            }

            // 2. Filtro Único Input hacia múltiples columnas (firstname / lastname)
            if (filter.getSearchUser() != null && !filter.getSearchUser().isBlank()) {

                // Hacemos el JOIN con el objeto interno de la Reserva (ej: "persona")
                Join<Object, Object> personaJoin = root.join("user");

                // Preparamos el texto en minúsculas y con comodines % para una búsqueda parcial
                String searchParam = "%" + filter.getSearchUser().toLowerCase() + "%";

                // Creamos las dos condiciones para la misma cadena recibida
                Predicate matchFirstname = cb.like(cb.lower(personaJoin.get("firstname")), searchParam);
                Predicate matchLastname = cb.like(cb.lower(personaJoin.get("lastname")), searchParam);


                predicates.add(cb.or(matchFirstname, matchLastname));
            }

            if (filter.getSearchBeachTent() != null) {
                var beachTentJoin = root.get("beachTent");

                predicates.add(cb.equal(beachTentJoin.get("number"), filter.getSearchBeachTent()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
