package com.balneamdp.service;

import com.balneamdp.DTO.request.SolicitudeRequestDto;
import com.balneamdp.DTO.response.SolicitudeResponseDto;
import com.balneamdp.exceptions.ResortNotFoundException;
import com.balneamdp.exceptions.ResourseNotFoundException;
import com.balneamdp.mapper.SolicitudeMapper;
import com.balneamdp.models.Row;
import com.balneamdp.models.SeaSideResort;
import com.balneamdp.models.Solicitude;
import com.balneamdp.models.User;
import com.balneamdp.repository.RowRepository;
import com.balneamdp.repository.SeaSideResortRepository;
import com.balneamdp.repository.SolicitudeRepository;
import com.balneamdp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SolicitudeService {
    private final SolicitudeRepository solicitudeRepository;
    private final SolicitudeMapper solicitudeMapper;

    private final SeaSideResortRepository seaSideResortRepository;
    private final RowRepository rowRepository;
    private final UserRepository userRepository;

    public SolicitudeResponseDto save(SolicitudeRequestDto request){
        SeaSideResort seaSideResort = seaSideResortRepository.findById(request.getSeaSideResortId())
                .orElseThrow(()->new ResourseNotFoundException("Balneario no encontrado"));

        Row row = rowRepository.findById(request.getRowId())
                .orElseThrow(()->new ResourseNotFoundException("Fila no encontrada"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(()->new ResourseNotFoundException("Usuario no encontrada"));

        Solicitude solicitude = solicitudeMapper.toEntity(request);
        solicitude.setSeaSideResort(seaSideResort);
        solicitude.setRow(row);
        solicitude.setUser(user);

        return solicitudeMapper.toDto(solicitudeRepository.save(solicitude));
    }

    public List<SolicitudeResponseDto> findAll(){
        return solicitudeRepository.findAll().stream()
                .map(solicitudeMapper::toDto)
                .toList();
    }

    public SolicitudeResponseDto findById(Long id){
        Solicitude solicitude = solicitudeRepository.findById(id)
                .orElseThrow(()->new ResourseNotFoundException("Solicitud no encontrada"));
        return solicitudeMapper.toDto(solicitude);
    }
}
