package com.balneamdp.controller;

import com.balneamdp.DTO.request.SolicitudeRequestDto;
import com.balneamdp.DTO.response.SolicitudeResponseDto;
import com.balneamdp.service.SolicitudeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/solicitude")
@AllArgsConstructor
public class SolicitudeController {

    private final SolicitudeService solicitudeService;

    @GetMapping
    public ResponseEntity<List<SolicitudeResponseDto>> getAll() {
        List<SolicitudeResponseDto> responses = solicitudeService.findAll();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolicitudeResponseDto> getById(@PathVariable Long id) {
        SolicitudeResponseDto response = solicitudeService.findById(id);
        return ResponseEntity.ok(response);
    }
}
