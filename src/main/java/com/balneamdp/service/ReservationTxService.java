package com.balneamdp.service;

import com.balneamdp.DTO.response.ReservationResponseDto;
import com.balneamdp.enums.ReservationState;
import com.balneamdp.exceptions.BeachTentAlreadyBookedException;
import com.balneamdp.mapper.ReservationMapper;
import com.balneamdp.models.Reservation;
import com.balneamdp.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationTxService {
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    @Transactional
    public Reservation confirmReservation(Reservation reservation, Long beachTentId, LocalDate startDate, LocalDate endDate){
        boolean ocupada = reservationRepository.existsOverlappingReservation(
                beachTentId,
                startDate,
                endDate,
                List.of(ReservationState.ACTIVA, ReservationState.PENDIENTE_DE_TEMPORADA)
        );

        if (ocupada) {
            throw new BeachTentAlreadyBookedException(
                    "La carpa #" + reservation.getBeachTent().getNumber() + " no está disponible para el período seleccionado.");
        }

        return reservationRepository.save(reservation);
    }
}