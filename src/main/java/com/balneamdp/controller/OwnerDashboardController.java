package com.balneamdp.controller;

import com.balneamdp.DTO.response.UserResponseDto;
import com.balneamdp.models.CustomUserDetails;
import com.balneamdp.service.SeaSideResortService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class OwnerDashboardController {
    private final SeaSideResortService seaSideResortService;

    //Lo hereda directamente del {resortName} de la URL
    @GetMapping("/clients")
    public ResponseEntity<List<UserResponseDto>> getClients(@AuthenticationPrincipal CustomUserDetails user){
        return ResponseEntity.ok(seaSideResortService.getClientsByOwner(user.getId()));
    }


}
