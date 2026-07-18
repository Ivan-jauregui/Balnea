package com.balneamdp.controller;

import com.balneamdp.models.Unit;
import com.balneamdp.service.UnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/unit")
@RequiredArgsConstructor
public class UnitController {
    private final UnitService unitService;

    private Unit save(Unit u){
        return unitService.save(u);
    }
}
