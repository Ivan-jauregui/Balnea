package com.balneamdp.service;

import com.balneamdp.DTO.SeaSideResortRequest;
import com.balneamdp.DTO.SeaSideResortResponse;
import com.balneamdp.exceptions.ResourseNotFoundException;
import com.balneamdp.mapper.MapperSeaSideResort;
import com.balneamdp.models.SeaSideResort;
import com.balneamdp.repository.SeaSideResortRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeaSideResortService {

    private final SeaSideResortRepository repository;
    private final MapperSeaSideResort mapper;

    public SeaSideResortResponse save(SeaSideResortRequest request) {
        SeaSideResort resort = mapper.toEntity(request);

        resort.setCreated_at(LocalDateTime.now());

        SeaSideResort savedResort = repository.save(resort);
        return mapper.toDto(savedResort);
    }


    public void deleteByName(String name) {
        SeaSideResort resort = repository.findByName(name)
                .orElseThrow(() -> new ResourseNotFoundException("Balneario con nombre " + name + " no encontrado"));

        repository.delete(resort);
    }


    public SeaSideResortResponse findByName(String name) {
        return repository.findByName(name)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourseNotFoundException("Balneario no encontrado"));
    }


    public List<SeaSideResortResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }
}