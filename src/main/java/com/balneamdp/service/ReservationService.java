package com.balneamdp.service;

import com.balneamdp.DTO.request.ReservationRequestDto;
import com.balneamdp.DTO.response.ReservationResponseDto;
import com.balneamdp.enums.PayState;
import com.balneamdp.enums.ReservationState;
import com.balneamdp.enums.ReservationType;
import com.balneamdp.exceptions.BeachTentAlreadyBookedException;
import com.balneamdp.exceptions.ResourseNotFoundException;
import com.balneamdp.mapper.ReservationMapper;
import com.balneamdp.models.*;
import com.balneamdp.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RowRepository rowRepository;
    private final BeachTentRepository beachTentRepository;
    private final UserRepository userRepository;
    private final SeaSideResortRepository seaSideResortRepository;
    private final RateSeaSideResortRepository rateSeaSideResortRepository;
    private final ReservationMapper mapper;
    private final Clock clock; // Inyectar Clock facilita pruebas unitarias de fechas

    @Transactional
    public ReservationResponseDto save(ReservationRequestDto request) {
        SeaSideResort resort = obtenerBalneario(request.getSeaSideResortId());
        User user = obtenerUsuario(request.getUserId());
        Row row = obtenerFila(request.getNumberRow(), resort);
        BeachTent beachTent = obtenerCarpa(resort, request.getNumberBeachTent());

        LocalDate startDate = request.getStartDate();
        LocalDate endDate = calcularFechaFin(startDate, request.getType(), resort);

        validarLimitesDeTemporada(startDate, endDate, resort);
        validarDisponibilidadCarpa(beachTent, startDate, endDate);

        RateSeaSideResort rate = obtenerTarifa(request.getType(), resort);

        Reservation reservation = mapper.toEntity(request);
        reservation.setSeaSideResort(resort);
        reservation.setUser(user);
        reservation.setRow(row);
        reservation.setBeachTent(beachTent);
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        reservation.setTotal(rate.getPrice());
        reservation.setReservationState(determinarEstadoInicialReserva(startDate));
        reservation.setPayState(determinarEstadoPagoInicial(request.getType()));

        Reservation saved = reservationRepository.save(reservation);
        return mapper.toDto(saved);
    }

    private SeaSideResort obtenerBalneario(Long id) {
        return seaSideResortRepository.findById(id)
                .orElseThrow(() -> new ResourseNotFoundException("Balneario no encontrado. ID: " + id));
    }

    private User obtenerUsuario(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourseNotFoundException("Usuario no encontrado. ID: " + id));
    }

    private Row obtenerFila(Integer numberRow, SeaSideResort resort) {
        return rowRepository.findByNumberAndSeaSideResort(numberRow, resort)
                .orElseThrow(() -> new ResourseNotFoundException("Fila no encontrada: " + numberRow));
    }

    private BeachTent obtenerCarpa(SeaSideResort resort, Integer numberBeachTent) {
        return beachTentRepository.findByRowSeaSideResortAndNumber(resort, numberBeachTent)
                .orElseThrow(() -> new ResourseNotFoundException("Carpa no encontrada número: " + numberBeachTent));
    }

    private RateSeaSideResort obtenerTarifa(ReservationType type, SeaSideResort resort) {
        return rateSeaSideResortRepository.findByReservationTypeAndSeaSideResort(type, resort)
                .orElseThrow(() -> new ResourseNotFoundException(
                        "No existe tarifa configurada para " + type + " en " + resort.getName()));
    }

    private void validarDisponibilidadCarpa(BeachTent tent, LocalDate start, LocalDate end) {
        // Validación de superposición real en BD:
        // Existen reservas que solapen el rango [start, end]
        boolean ocupada = reservationRepository.existsOverlappingReservation(
                tent.getId(),
                start,
                end,
                List.of(ReservationState.ACTIVA, ReservationState.PENDIENTE_DE_TEMPORADA)
        );

        if (ocupada) {
            throw new BeachTentAlreadyBookedException(
                    "La carpa #" + tent.getNumber() + " no está disponible para el período seleccionado.");
        }
    }

    private LocalDate calcularFechaFin(LocalDate start, ReservationType type, SeaSideResort resort) {
        return switch (type) {
            case DIA -> start;
            case QUINCENA -> {
                LocalDate fortnightEnd = start.plusDays(14);
                yield fortnightEnd.isAfter(resort.getEndDate()) ? resort.getEndDate() : fortnightEnd;
            }
            case TEMPORADA -> resort.getEndDate();
        };
    }

    private void validarLimitesDeTemporada(LocalDate start, LocalDate end, SeaSideResort resort) {
        if (start.isBefore(resort.getStartDate()) || end.isAfter(resort.getEndDate())) {
            throw new IllegalArgumentException(String.format(
                    "El balneario opera del %s al %s. El rango solicitado (%s a %s) está fuera de temporada.",
                    resort.getStartDate(), resort.getEndDate(), start, end));
        }
    }

    private ReservationState determinarEstadoInicialReserva(LocalDate startDate) {
        LocalDate today = LocalDate.now(clock);

        if (startDate.isBefore(today)) {
            throw new IllegalArgumentException("No se pueden realizar reservas para fechas pasadas.");
        }

        return startDate.isAfter(today) ? ReservationState.PENDIENTE_DE_TEMPORADA : ReservationState.ACTIVA;
    }

    private PayState determinarEstadoPagoInicial(ReservationType type) {
        return (type == ReservationType.QUINCENA || type == ReservationType.TEMPORADA)
                ? PayState.PENDIENTE
                : PayState.PAGADO;
    }
}