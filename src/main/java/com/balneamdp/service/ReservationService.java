package com.balneamdp.service;

import com.balneamdp.DTO.request.ReservationRequestDto;
import com.balneamdp.DTO.response.ReservationResponseDto;
import com.balneamdp.enums.PayState;
import com.balneamdp.enums.ReservationState;
import com.balneamdp.exceptions.BeachTentAlreadyBookedException;
import com.balneamdp.exceptions.ResourseNotFoundException;
import com.balneamdp.mapper.ReservationMapper;
import com.balneamdp.models.*;
import com.balneamdp.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository repository;
    private final RowRepository rowRepository;
    private final UserRepository userRepository;
    private final SeaSideResortRepository seaSideResortRepository;
    private final ReservationMapper mapper;

    @Transactional
    public ReservationResponseDto save(ReservationRequestDto request) {
        // 1. Carga de datos y entidades relacionadas
        SeaSideResort seaSideResort = obtenerBalneario(request.getSeaSideResortId());
        User user = obtenerUsuario(request.getUserId());
        Row row = obtenerFila(request.getNumberRow(), seaSideResort);

        // 2. Construcción inicial de la entidad
        Reservation reservation = inicializarReserva(request, user, row, seaSideResort);

        // 3. Cálculos de negocio y asignación de tiempos
        calcularPeriodoReserva(reservation, seaSideResort);

        // 4. Validaciones estrictas de fechas y disponibilidad de la carpa
        validarLimitesDeTemporada(reservation.getStartDate(), seaSideResort);
        validarDisponibilidadCarpa(reservation, seaSideResort);

        // 5. Asignación de estados finales basados en políticas del negocio
        reservation.setReservationState(determinarEstadoInicialReserva(seaSideResort.getStartDate()));
        reservation.setPayState(determinarEstadoPagoInicial(request.getType().toString().toUpperCase()));

        // 6. Persistencia y respuesta
        Reservation savedReservation = repository.save(reservation);
        return mapper.toDto(savedReservation);
    }

    // ==========================================
    // MÉTODOS MODULARIZADOS DE BÚSQUEDA
    // ==========================================

    private SeaSideResort obtenerBalneario(Long id) {
        return seaSideResortRepository.findById(id)
                .orElseThrow(() -> new ResourseNotFoundException("Balneario no encontrado"));
    }

    private User obtenerUsuario(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourseNotFoundException("El usuario no fue encontrado"));
    }

    private Row obtenerFila(Integer numberRow, SeaSideResort resort) {
        return rowRepository.findByNumberAndSeaSideResort(numberRow, resort)
                .orElseThrow(() -> new ResourseNotFoundException("La fila no fue encontrada"));
    }

    // ==========================================
    // MÉTODOS DE ESTRUCTURA Y CÁLCULO
    // ==========================================

    private Reservation inicializarReserva(ReservationRequestDto request, User user, Row row, SeaSideResort resort) {
        Reservation reservation = mapper.toEntity(request);
        reservation.setSeaSideResort(resort);
        reservation.setUser(user);
        reservation.setRow(row);
       reservation.setStartDate(resort.getStartDate());
        return reservation;
    }

    private void calcularPeriodoReserva(Reservation reservation, SeaSideResort resort) {
        LocalDateTime start = resort.getStartDate();
        LocalDateTime endSeason = resort.getEndDate();

        String tipo = reservation.getType().toString().toUpperCase();

        switch (tipo) {
            case "DIA":
                reservation.setEndDate(start);
                break;
            case "QUINCENA":
                LocalDateTime endFortnight = start.plusDays(14);
                reservation.setEndDate(endFortnight.isAfter(endSeason) ? endSeason : endFortnight);
                break;
            case "TEMPORADA":
                reservation.setEndDate(endSeason);
                break;
            default:
                throw new IllegalArgumentException("Tipo de reserva no reconocido: " + tipo);
        }
    }

    // ==========================================
    // MÉTODOS DE VALIDACIÓN
    // ==========================================

    private void validarLimitesDeTemporada(LocalDateTime startRequested, SeaSideResort resort) {
        if (startRequested.isBefore(resort.getStartDate()) || startRequested.isAfter(resort.getEndDate())) {
            throw new IllegalArgumentException("No puedes reservar para esa fecha. El balneario opera únicamente desde el "
                    + resort.getStartDate() + " hasta el " + resort.getEndDate());
        }
    }

    private void validarDisponibilidadCarpa(Reservation reservation, SeaSideResort resort) {
        boolean yaEstaOcupada = repository.existsByNumberBeachTentAndSeaSideResortAndReservationStateAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                reservation.getNumberBeachTent(),
                resort,
                ReservationState.ACTIVA,
                reservation.getEndDate(),
                reservation.getStartDate()
        );

        if (yaEstaOcupada) {
            throw new BeachTentAlreadyBookedException("La carpa número " + reservation.getNumberBeachTent() + " ya está reservada y activa.");
        }
    }

    // ==========================================
    // MÉTODOS DE POLÍTICAS DE ESTADO
    // ==========================================

    private ReservationState determinarEstadoInicialReserva(LocalDateTime resortStartDate) {
        LocalDateTime today = LocalDateTime.now();
        int mesActual = today.getMonthValue();
        if (mesActual >= 10 || mesActual <= 4) {
            if (!today.isBefore(resortStartDate)) {
                return ReservationState.ACTIVA;
            }
            return ReservationState.PENDIENTE_DE_TEMPORADA;
        } else {
            throw new IllegalArgumentException("No puedes reservar fuera de temporada");
        }
    }

    private PayState determinarEstadoPagoInicial(String tipo) {
        if ("QUINCENA".equals(tipo) || "TEMPORADA".equals(tipo)) {
            return PayState.PENDIENTE;
        }
        return PayState.PAGADO;
    }
}
