package com.balneamdp.service;

import com.balneamdp.DTO.request.ReservationRequestDto;
import com.balneamdp.DTO.response.ReservationResponseDto;
import com.balneamdp.enums.PayState;
import com.balneamdp.enums.ReservationState;
import com.balneamdp.enums.ReservationType;
import com.balneamdp.exceptions.ResourseNotFoundException;
import com.balneamdp.mapper.ReservationMapper;
import com.balneamdp.models.*;
import com.balneamdp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Subtask;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationTxService reservationTxService;
    private final ReservationRepository reservationRepository;
    private final RowRepository rowRepository;
    private final BeachTentRepository beachTentRepository;
    private final UserRepository userRepository;
    private final SeaSideResortRepository seaSideResortRepository;
    private final RateSeaSideResortRepository rateSeaSideResortRepository;
    private final ReservationMapper mapper;

    // Records privados para empaquetar los resultados de cada fase concurrente
    private record InitialContext(SeaSideResort resort, User user) {}
    private record DetailsContext(Row row, BeachTent beachTent, RateSeaSideResort rate) {}

    public ReservationResponseDto save(ReservationRequestDto request) {

        // 1. Fase 1 Concurrente: Consultar Balneario y Usuario
        InitialContext initial = consultarDatosInicialesEnParalelo(request.getSeaSideResortId(), request.getUserId());

        // 2. Validaciones de Fechas (En Memoria / CPU)
        LocalDate startDate = request.getStartDate();
        LocalDate endDate = calcularFechaFin(startDate, request.getType(), initial.resort());
        validarLimitesDeTemporada(startDate, endDate, initial.resort());

        // 3. Fase 2 Concurrente: Consultar Fila, Carpa y Tarifa
        DetailsContext details = consultarDetallesEnParalelo(request, initial.resort());

        // 4. Mapeo modularizado de la Entidad
        Reservation reservation = construirEntidadReserva(
                request, initial.resort(), initial.user(),
                details.row(), details.beachTent(), details.rate(),
                startDate, endDate
        );

        // 5. Guardado atómico con Transacción Corta (<5ms)
        Reservation saved = reservationTxService.confirmReservation(
                reservation,
                details.beachTent().getId(),
                startDate,
                endDate
        );

        return mapper.toDto(saved);
    }


    // HELPER CONCURRENTE 1: Fase 1 (Virtual Threads)
    // =========================================================================
    private InitialContext consultarDatosInicialesEnParalelo(Long resortId, Long userId) {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {

            Subtask<SeaSideResort> resortTask = scope.fork(() -> obtenerBalneario(resortId));
            Subtask<User> userTask          = scope.fork(() -> obtenerUsuario(userId));

            scope.join();
            scope.throwIfFailed();

            return new InitialContext(resortTask.get(), userTask.get());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupción al consultar balneario y usuario", e);
        } catch (Exception e) {
            throw rethrowUnchecked(e);
        }
    }


    // HELPER CONCURRENTE 2: Fase 2 (Virtual Threads)
    // =========================================================================
    private DetailsContext consultarDetallesEnParalelo(ReservationRequestDto request, SeaSideResort resort) {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {

            Subtask<Row> rowTask            = scope.fork(() -> obtenerFila(request.getNumberRow(), resort));
            Subtask<BeachTent> tentTask     = scope.fork(() -> obtenerCarpa(resort, request.getNumberBeachTent()));
            Subtask<RateSeaSideResort> rateTask = scope.fork(() -> obtenerTarifa(request.getType(), resort));

            scope.join();
            scope.throwIfFailed();

            return new DetailsContext(rowTask.get(), tentTask.get(), rateTask.get());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupción al consultar fila, carpa y tarifa", e);
        } catch (Exception e) {
            throw rethrowUnchecked(e);
        }
    }


    // HELPER DE MAPEO
    // =========================================================================
    private Reservation construirEntidadReserva(
            ReservationRequestDto request, SeaSideResort resort, User user,
            Row row, BeachTent beachTent, RateSeaSideResort rate,
            LocalDate startDate, LocalDate endDate) {

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
        return reservation;
    }


    // HELPERS DE BÚSQUEDA (Repositorios)
    // =========================================================================
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


    // HELPERS DE REGLAS DE NEGOCIO
    // =========================================================================
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
        LocalDate today = LocalDate.now();
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

    // Helper para relanzar excepciones checked como unchecked de forma limpia
    private RuntimeException rethrowUnchecked(Exception e) {
        if (e instanceof RuntimeException re) return re;
        return new RuntimeException(e);
    }
}