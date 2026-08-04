package com.balneamdp.service;

import com.balneamdp.DTO.response.BeachTentResponseDto;
import com.balneamdp.DTO.response.UserResponseDto;
import com.balneamdp.exceptions.ResourseNotFoundException;
import com.balneamdp.mapper.BeachTentMapper;
import com.balneamdp.mapper.UserMapper;
import com.balneamdp.models.BeachTent;
import com.balneamdp.models.Reservation;
import com.balneamdp.repository.BeachTentRepository;
import com.balneamdp.repository.ReservationRepository;
import com.balneamdp.repository.SeaSideResortRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ResortDashboardService {
    private final SeaSideResortRepository seaSideResortRepository;
    private final BeachTentRepository beachTentRepository;
    private final ReservationRepository reservationRepository;
    private final UserMapper userMapper;
    private final BeachTentMapper beachTentMapper;

    public List<UserResponseDto> getClients(Long seaSideResortId){
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

    //Metodo helper para validar la existencia del balneario
    private void validateResortExists(Long seaSideResortId) {
        if (!seaSideResortRepository.existsById(seaSideResortId)) {
            throw new ResourseNotFoundException("Balneario no fue encontrado");
        }
    }

    public double getMonthlyRevenue(Long seaSideResortId) {
        validateResortExists(seaSideResortId);

        int currentMonth = LocalDateTime.now().getMonthValue();
        int currentYear = LocalDateTime.now().getYear();
        return reservationRepository.getMonthlyRevenueByResortAndMonth(seaSideResortId,currentMonth,currentYear);
    }

}
