package com.balneamdp.service;

import com.balneamdp.models.Unit;
import com.balneamdp.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnitService {
    private final UnitRepository unitRepository;

    public Unit save(Unit u){
        return unitRepository.save(u);
    }
}
