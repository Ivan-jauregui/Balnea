package com.balneamdp.controller;

import com.balneamdp.models.Amenity;
import com.balneamdp.service.AmenityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/servicios")
@RequiredArgsConstructor
public class AmenityController {
    private final AmenityService service;

    @PostMapping
    public ResponseEntity<Amenity> save(@RequestBody Amenity amenity){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(amenity));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteByName(@PathVariable String name){
        service.deleteByName(name);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/{name}")
    public ResponseEntity<Amenity> findByName(@PathVariable String name){
        return ResponseEntity.ok(service.findByName(name));
    }

    @GetMapping
    public ResponseEntity<List<Amenity>> findAll(){
        return  ResponseEntity.ok(service.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Amenity> update(@PathVariable Long id,@RequestBody Amenity amenity){
        return ResponseEntity.ok(service.update(id,amenity));
    }
}
