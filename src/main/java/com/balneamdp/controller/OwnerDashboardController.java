package com.balneamdp.controller;

import com.balneamdp.DTO.response.BeachTentResponseDto;
import com.balneamdp.DTO.response.UserResponseDto;
import com.balneamdp.service.ResortDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class OwnerDashboardController {
    private final ResortDashboardService resortDashboardService;

    @GetMapping("/{seaSideResortId}/clients")
    @PreAuthorize("@resortSecurity.isOwner(#seaSideResortId, authentication.name)")
    public ResponseEntity<List<UserResponseDto>> getClients(@PathVariable Long seaSideResortId) {
        return ResponseEntity.ok(resortDashboardService.getClients(seaSideResortId));
    }

    @GetMapping("/beach-tents/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BeachTentResponseDto>> getBeachTents(@PathVariable Long id){
        return ResponseEntity.ok(resortDashboardService.getBeachTents(id));
    }

}
