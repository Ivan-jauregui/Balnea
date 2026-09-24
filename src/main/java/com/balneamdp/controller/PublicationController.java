package com.balneamdp.controller;

import com.balneamdp.DTO.response.PublicationResponseDto;
import com.balneamdp.service.PublicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/publication")
@RequiredArgsConstructor
public class PublicationController {
    private final PublicationService publicationService;

    @GetMapping("balneario/{id}")
    public ResponseEntity<List<PublicationResponseDto>> getAllPublicationsBySeaSideResortId(@PathVariable Long id){
        return ResponseEntity.ok(publicationService.findAllPublicationBySeaSideResort(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicationResponseDto> findById(@PathVariable Long id){
        return ResponseEntity.ok(publicationService.findById(id));
    }
}
