package com.balneamdp.service;

import com.balneamdp.DTO.response.BeachTentResponseDto;
import com.balneamdp.DTO.response.UserResponseDto;
import com.balneamdp.exceptions.ResourseNotFoundException;
import com.balneamdp.mapper.BeachTentMapper;
import com.balneamdp.mapper.UserMapper;
import com.balneamdp.models.BeachTent;
import com.balneamdp.repository.BeachTentRepository;
import com.balneamdp.repository.ReservationRepository;
import com.balneamdp.repository.SeaSideResortRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ResortDashboardService {

    private final SeaSideResortRepository seaSideResortRepository;
    private final BeachTentRepository beachTentRepository;
    private final ReservationRepository reservationRepository;
    private final UserMapper userMapper;
    private final BeachTentMapper beachTentMapper;

    public List<UserResponseDto> getClients(Long seaSideResortId) {
        validateResortExists(seaSideResortId);

        return reservationRepository.findDistinctClientsBySeaSideResortId(seaSideResortId).stream()
                .map(userMapper::toDto)
                .toList();
    }

    public List<BeachTentResponseDto> getBeachTents(Long seaSideResortId) {
        validateResortExists(seaSideResortId);

        List<BeachTent> beachTents = beachTentRepository.findByRowSeaSideResortId(seaSideResortId);
        return beachTents.stream()
                .map(beachTentMapper::toDto)
                .toList();
    }

    public double getMonthlyRevenue(Long seaSideResortId) {
        validateResortExists(seaSideResortId);

        int currentMonth = LocalDateTime.now().getMonthValue();
        int currentYear = LocalDateTime.now().getYear();

        Double total = reservationRepository.getTotalRevenueByResortAndMonthAndYear(seaSideResortId, currentMonth, currentYear);
        return Optional.ofNullable(total).orElse(0.0);
    }

    public Map<Integer, Double> getYearRevenue(Long seaSideResortId) {
        validateResortExists(seaSideResortId);

        Map<Integer, Double> annualRevenue = new LinkedHashMap<>();
        for (int month = 1; month <= 12; month++) {
            annualRevenue.put(month, 0.0);
        }

        int currentYear = LocalDate.now().getYear();
        List<Object[]> results = reservationRepository.getAnnualMonthlyRevenueBreakdownByResort(seaSideResortId, currentYear);

        for (Object[] row : results) {
            Integer month = ((Number) row[0]).intValue();
            Double revenue = ((Number) row[1]).doubleValue();
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