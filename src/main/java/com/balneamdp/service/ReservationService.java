package com.balneamdp.service;

import com.balneamdp.DTO.request.ReservationRequestDto;
import com.balneamdp.DTO.response.ReservationResponseDto;
import com.balneamdp.exceptions.ResourseNotFoundException;
import com.balneamdp.mapper.ReservationMapper;
import com.balneamdp.models.*;
import com.balneamdp.repository.ReservationRepository;
import com.balneamdp.repository.SeaSideResortRepository;
import com.balneamdp.repository.UnitRepository;
import com.balneamdp.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final SeaSideResortService seaSideResortService;
    private final ReservationRepository repository;
    private final UnitRepository unitRepository;
    private final UserRepository userRepository;
    private final SeaSideResortRepository seaSideResortRepository;
    private final ReservationMapper mapper;

    @Transactional
    public ReservationResponseDto save(ReservationRequestDto request){
        int randomIndex = (int) (Math.random()*seaSideResortService.getUnits(request.getSeaSideResortId()).size());
        Unit availableUnit = seaSideResortService.getAllPendingUnits(request.getSeaSideResortId()).get(randomIndex);
        if (availableUnit==null){
            throw  new ResourseNotFoundException("No hay carpa disponibles");
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(()->new ResourseNotFoundException("El usuario no fue encontrado"));

        SeaSideResort seaSideResort = seaSideResortRepository.findById(request.getSeaSideResortId())
                .orElseThrow(()-> new ResourseNotFoundException("Balneario no encontrado"));

        Reservation reservation = mapper.toEntity(request);
        reservation.setSeaSideResort(seaSideResort);
        reservation.setUser(user);
        reservation.setUnit(availableUnit);

        availableUnit.setPayState(PayState.PAGADO);

        repository.save(reservation);

        return mapper.toDto(reservation);
    }

}
