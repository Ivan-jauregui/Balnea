package com.balneamdp.controller;

import com.balneamdp.DTO.request.PublicationRequestDto;
import com.balneamdp.DTO.request.ReservationFitlerDto;
import com.balneamdp.DTO.response.BeachTentResponseDto;
import com.balneamdp.DTO.response.PublicationResponseDto;
import com.balneamdp.DTO.response.ReservationResponseDto;
import com.balneamdp.DTO.response.UserResponseDto;
import com.balneamdp.service.PublicationService;
import com.balneamdp.service.ResortDashboardService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/{seaSideResortId}/dashboard")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN') and @securityService.casAccessSeaSideResort(#seaSideResortId, authentication.name)")
public class OwnerDashboardController {
    private final ResortDashboardService resortDashboardService;
    private final PublicationService publicationService;


    /* ---Metrics--- */
    @GetMapping("/monthly-revenue")
    public ResponseEntity<BigDecimal> getMonthlyRevenue(@PathVariable Long seaSideResortId){
        return ResponseEntity.ok(resortDashboardService.getMonthlyRevenue(seaSideResortId));
    }
    @GetMapping("/year-revenue")
    public ResponseEntity<Map<Integer,BigDecimal>> getYearRevenue(@PathVariable Long seaSideResortId){
        return ResponseEntity.ok(resortDashboardService.getYearRevenue(seaSideResortId));
    }


    /* ---General Information--- */
    @GetMapping("/clients")
    public ResponseEntity<List<UserResponseDto>> getClients(@PathVariable Long seaSideResortId) {
        return ResponseEntity.ok(resortDashboardService.getClients(seaSideResortId));
    }
    @GetMapping
    public ResponseEntity<Page<ReservationResponseDto>> getReservations(
            @ModelAttribute ReservationFitlerDto filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(resortDashboardService.getReservations(filter, page, size));
    }
    @GetMapping("/beach-tents")
    public ResponseEntity<List<BeachTentResponseDto>> getBeachTents(@PathVariable Long seaSideResortId){
        return ResponseEntity.ok(resortDashboardService.getBeachTents(seaSideResortId));
    }


    /* ---Publication--- */
    @PostMapping(value = "/publication", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PublicationResponseDto> addPublication(@PathVariable("seaSideResortId") Long seaSideResortId,
                                                                 @ModelAttribute @Valid PublicationRequestDto request){
        PublicationResponseDto response = publicationService.save(request,seaSideResortId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping("publications")
    public ResponseEntity<List<PublicationResponseDto>> findAllPublicationBySeaSideResort(@PathVariable("seaSideResortId") Long seaSideResortId){
        return  ResponseEntity.ok(publicationService.findAllPublicationBySeaSideResort(seaSideResortId));
    }
    @GetMapping("publication/{id}")
    public ResponseEntity<PublicationResponseDto> findByIdPublication(@PathVariable("id") Long id){
        return  ResponseEntity.ok(publicationService.findById(id));
    }
    @DeleteMapping("publications/{id}")
    public ResponseEntity<Void> deletePublcationById(@PathVariable Long id){
        publicationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
