package com.balneamdp.service;

import com.balneamdp.DTO.request.ReservationFitlerDto;
import com.balneamdp.DTO.response.BeachTentResponseDto;
import com.balneamdp.DTO.response.ReservationResponseDto;
import com.balneamdp.DTO.response.UserResponseDto;
import com.balneamdp.exceptions.ResourseNotFoundException;
import com.balneamdp.mapper.BeachTentMapper;
import com.balneamdp.mapper.ReservationMapper;
import com.balneamdp.mapper.UserMapper;
import com.balneamdp.models.BeachTent;
import com.balneamdp.models.Reservation;
import com.balneamdp.repository.BeachTentRepository;
import com.balneamdp.repository.ReservationRepository;
import com.balneamdp.repository.SeaSideResortRepository;
import com.balneamdp.repository.specification.ReservationSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ResortDashboardService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final SeaSideResortRepository seaSideResortRepository;
    private final BeachTentRepository beachTentRepository;
    private final UserMapper userMapper;
    private final BeachTentMapper beachTentMapper;

    public Page<ReservationResponseDto> getReservations(ReservationFitlerDto filter, int page, int size) {
        if (filter == null) {
            filter = new ReservationFitlerDto();
        }

        Specification<Reservation> spec = ReservationSpecification.byFilter(filter);
        Pageable pageable = PageRequest.of(page, size);

        return reservationRepository.findAll(spec, pageable).map(reservationMapper::toDto);
    }

    public List<UserResponseDto> getClients(Long seaSideResortId) {
        validateResortExists(seaSideResortId);

        return reservationRepository.findDistinctClientsBySeaSideResortId(seaSideResortId).stream()
                .map(userMapper::toDto)
                .toList();
    }

    public List<BeachTentResponseDto> getBeachTents(Long seaSideResortId) {
        validateResortExists(seaSideResortId);

        // Se usa la navegación adecuada para la relación
        List<BeachTent> beachTents = beachTentRepository.findByRow_SeaSideResort_Id(seaSideResortId);
        return beachTents.stream()
                .map(beachTentMapper::toDto)
                .toList();
    }

    public BigDecimal getMonthlyRevenue(Long seaSideResortId) {
        validateResortExists(seaSideResortId);

        LocalDate now = LocalDate.now();
        BigDecimal total = reservationRepository.getTotalRevenueByResortAndMonthAndYear(
                seaSideResortId,
                now.getMonthValue(),
                now.getYear()
        );

        return Optional.ofNullable(total).orElse(BigDecimal.ZERO);
    }

    public Map<Integer, BigDecimal> getYearRevenue(Long seaSideResortId) {
        validateResortExists(seaSideResortId);

        Map<Integer, BigDecimal> annualRevenue = new LinkedHashMap<>();
        for (int month = 1; month <= 12; month++) {
            annualRevenue.put(month, BigDecimal.ZERO);
        }

        int currentYear = LocalDate.now().getYear();
        List<Object[]> results = reservationRepository.getAnnualMonthlyRevenueBreakdownByResort(seaSideResortId, currentYear);

        for (Object[] row : results) {
            Integer month = ((Number) row[0]).intValue();
            BigDecimal revenue = (row[1] instanceof BigDecimal bd)
                    ? bd
                    : BigDecimal.valueOf(((Number) row[1]).doubleValue());

            annualRevenue.put(month, revenue);
        }

        return annualRevenue;
    }

    private void validateResortExists(Long seaSideResortId) {
        if (!seaSideResortRepository.existsById(seaSideResortId)) {
            throw new ResourseNotFoundException("Balneario no fue encontrado");
        }
    }
}