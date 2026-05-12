package com.balneamdp.controller;

import com.balneamdp.DTO.SeaSideResortRequest;
import com.balneamdp.DTO.SeaSideResortResponse;
import com.balneamdp.models.SeaSideResort;
import com.balneamdp.service.SeaSideResortService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/balnearios")
@RequiredArgsConstructor
public class SeaSideResortController {
    private final SeaSideResortService service;

    @PostMapping()
    public ResponseEntity<SeaSideResortResponse> save(@Valid @RequestBody SeaSideResortRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(request));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteByName(@PathVariable  String name){
        service.deleteByName(name);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{name}")
    public ResponseEntity<SeaSideResortResponse> findByName(@PathVariable String name){
        return ResponseEntity.ok(service.findByName(name));
    }

    @GetMapping()
    public ResponseEntity<List<SeaSideResortResponse>> findAll(){
        return  ResponseEntity.ok(service.findAll());
    }

}
