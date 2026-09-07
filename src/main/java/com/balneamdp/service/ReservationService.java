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
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Subtask;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private static final long DIAS_MINIMOS_PAGO_PARCIAL = 15;
    private static final BigDecimal PORCENTAJE_SENYA = new BigDecimal("0.50");

    private record InitialContext(SeaSideResort resort, User user) {}
    private record DetailsContext(Row row, BeachTent beachTent) {}

    private final ReservationRepository reservationRepository;
    private final RowRepository rowRepository;
    private final BeachTentRepository beachTentRepository;
    private final UserRepository userRepository;
    private final SeaSideResortRepository seaSideResortRepository;
    private final RateSeaSideResortRepository rateSeaSideResortRepository;
    private final ReservationMapper mapper;

    private final ReservationTxService reservationTxService;
    private final PaymentService paymentService;

    public ReservationResponseDto save(ReservationRequestDto request) throws MPException, MPApiException {

        LocalDate startDate = request.getStartDate();
        LocalDate endDate = request.getEndDate();

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de fin.");
        }

        InitialContext initial = consultarDatosInicialesEnParalelo(request.getSeaSideResortId(), request.getUserId());

        long days = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        validarLimitesDeTemporada(startDate, endDate, initial.resort());
        BigDecimal total = obtenerTotal(days, startDate, endDate, request.isPayPartial(), initial.resort());

        DetailsContext details = consultarDetallesEnParalelo(request, initial.resort());

        Reservation reservation = construirEntidadReserva(
                request, initial.resort(), initial.user(),
                details.row(), details.beachTent(), total,
                startDate, endDate, days
        );

        // Transacción acotada (alta concurrencia / Virtual Threads)
        Reservation saved = reservationTxService.confirmReservation(
                reservation,
                details.beachTent().getId(),
                startDate,
                endDate
        );

        ReservationResponseDto response = mapper.toDto(saved);

        // Mercado Pago Integration
        String initPoint = paymentService.createPreference(
                saved.getId(),
                initial.resort().getName(),
                details.beachTent().getNumber(),
                total,
                initial.user().getEmail()
        );

        response.setInitPoint(initPoint);
        response.setNumberBeachTent(details.beachTent.getNumber());
        response.setTotal(total);

        return response;
    }

    private InitialContext consultarDatosInicialesEnParalelo(Long resortId, Long userId) {
        try (var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.awaitAllSuccessfulOrThrow())) {

            Subtask<SeaSideResort> resortTask = scope.fork(() -> obtenerBalneario(resortId));
            Subtask<User> userTask = scope.fork(() -> obtenerUsuario(userId));

            scope.join();

            return new InitialContext(resortTask.get(), userTask.get());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupción al consultar balneario y usuario", e);
        } catch (Exception e) {
            throw rethrowUnchecked(e);
        }
    }

    private DetailsContext consultarDetallesEnParalelo(ReservationRequestDto request, SeaSideResort resort) {
        try (var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.awaitAllSuccessfulOrThrow())) {

            Subtask<Row> rowTask        = scope.fork(() -> obtenerFila(request.getNumberRow(), resort));
            Subtask<BeachTent> tentTask = scope.fork(() -> obtenerCarpa(resort, request.getNumberBeachTent()));

            scope.join();

            return new DetailsContext(rowTask.get(), tentTask.get());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupción al consultar fila y carpa", e);
        } catch (Exception e) {
            throw rethrowUnchecked(e);
        }
    }

    // HELPER DE MAPEO
    // =========================================================================
    private Reservation construirEntidadReserva(
            ReservationRequestDto request, SeaSideResort resort, User user,
            Row row, BeachTent beachTent, BigDecimal total,
            LocalDate startDate, LocalDate endDate, long days) {

        Reservation reservation = mapper.toEntity(request);
        reservation.setSeaSideResort(resort);
        reservation.setUser(user);
        reservation.setRow(row);
        reservation.setBeachTent(beachTent);
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        reservation.setTotal(total);
        reservation.setReservationState(determinarEstadoInicialReserva(startDate, resort));
        reservation.setPayState(determinarEstadoPagoInicial(days, request.isPayPartial()));

        return reservation;
    }

    // CONSULTAS A REPOSITORIOS
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

    // REGLAS DE NEGOCIO
    // =========================================================================
    private BigDecimal obtenerTotal(long days, LocalDate startDate, LocalDate endDate, boolean payPartial, SeaSideResort seaSideResort) {
        RateSeaSideResort rate = rateSeaSideResortRepository.findBySeaSideResortId(seaSideResort.getId());

        if (rate == null) {
            throw new ResourseNotFoundException("No existen tarifas configuradas para el balneario ID: " + seaSideResort.getId());
        }

        BigDecimal totalBase;

        if (startDate.equals(seaSideResort.getStartDate()) && endDate.equals(seaSideResort.getEndDate())) {
            totalBase = rate.getSeasonalPrice();
        } else {
            totalBase = rate.getBasePrice().multiply(BigDecimal.valueOf(days));
        }

        if (payPartial) {
            if (days < DIAS_MINIMOS_PAGO_PARCIAL) {
                throw new IllegalArgumentException("El pago parcial solo está disponible para reservas de 15 días o más.");
            }
            return totalBase.multiply(PORCENTAJE_SENYA).setScale(2, RoundingMode.HALF_UP);
        }

        return totalBase.setScale(2, RoundingMode.HALF_UP);
    }

    private void validarLimitesDeTemporada(LocalDate start, LocalDate end, SeaSideResort resort) {
        if (start.isBefore(resort.getStartDate()) || end.isAfter(resort.getEndDate())) {
            throw new IllegalArgumentException(String.format(
                    "El balneario opera del %s al %s. El rango solicitado (%s a %s) está fuera de temporada.",
                    resort.getStartDate(), resort.getEndDate(), start, end));
        }
    }

    private ReservationState determinarEstadoInicialReserva(LocalDate startDate, SeaSideResort resort) {
        LocalDate today = LocalDate.now();

        if (startDate.isBefore(today)) {
            throw new IllegalArgumentException("No se pueden realizar reservas para fechas pasadas.");
        }

        if (today.isBefore(resort.getStartDate())) {
            return ReservationState.PENDIENTE_DE_TEMPORADA;
        }
        return ReservationState.ACTIVA;
    }

    private PayState determinarEstadoPagoInicial(long days, boolean payPartial) {
        if (days >= DIAS_MINIMOS_PAGO_PARCIAL && payPartial) {
            return PayState.SEÑADO;
        }
        return PayState.PENDIENTE;
    }

    @SuppressWarnings("unchecked")
    private static <T extends Throwable> T rethrowUnchecked(Throwable exception) throws T {
        throw (T) exception;
    }
}