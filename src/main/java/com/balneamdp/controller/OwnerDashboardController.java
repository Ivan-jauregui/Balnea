package com.balneamdp.controller;

import com.balneamdp.DTO.SeaSideResortResponse;
import com.balneamdp.DTO.request.ReservationFitlerDto;
import com.balneamdp.DTO.request.SeaSideResortFilterDto;
import com.balneamdp.DTO.response.BeachTentResponseDto;
import com.balneamdp.DTO.response.UserResponseDto;
import com.balneamdp.service.ResortDashboardService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/{seaSideResortId}/dashboard")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN') and @resortSecurity.isOwner(#seaSideResortId, authentication.name)")
public class OwnerDashboardController {
    private final ResortDashboardService resortDashboardService;

    @GetMapping("/{seaSideResortId}/clients")
    public ResponseEntity<List<UserResponseDto>> getClients(@PathVariable Long seaSideResortId) {
        return ResponseEntity.ok(resortDashboardService.getClients(seaSideResortId));
    }

    @GetMapping("/beach-tents/{seaSideResortId}")
    public ResponseEntity<List<BeachTentResponseDto>> getBeachTents(@PathVariable Long seaSideResortId){
        return ResponseEntity.ok(resortDashboardService.getBeachTents(seaSideResortId));
    }
    @GetMapping("/{seaSideResortId}/monthly-revenue")
    public ResponseEntity<Double> getMonthlyRevenue(@PathVariable Long seaSideResortId){
        return ResponseEntity.ok(resortDashboardService.getMonthlyRevenue(seaSideResortId));
    }
    @GetMapping("/{seaSideResortId}/year-revenue")
    public ResponseEntity<Map<Integer,Double>> getYearRevenue(@PathVariable Long seaSideResortId){
        return ResponseEntity.ok(resortDashboardService.getYearRevenue(seaSideResortId));
    }
    @GetMapping
    public ResponseEntity<Page<SeaSideResortResponse>> getReservations(
            @ModelAttribute ReservationFitlerDto filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(resortDashboardService.getReservations(filter, page, size));
    }
}
