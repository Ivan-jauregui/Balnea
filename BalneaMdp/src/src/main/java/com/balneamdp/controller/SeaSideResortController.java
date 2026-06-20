package com.balneamdp.controller;

import com.balneamdp.DTO.SeaSideResortRequest;
import com.balneamdp.DTO.SeaSideResortResponse;
import com.balneamdp.DTO.request.ReservationRequestDto;
import com.balneamdp.DTO.response.CommentResponseDto;
import com.balneamdp.DTO.response.ReservationResponseDto;
import com.balneamdp.models.Comments;
import com.balneamdp.models.SeaSideResort;
import com.balneamdp.models.Unit;
import com.balneamdp.service.ReservationService;
import com.balneamdp.service.SeaSideResortService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.events.Comment;
import java.util.List;

@RestController
@RequestMapping("/api/v1/balnearios")
@RequiredArgsConstructor
public class SeaSideResortController {
    private final SeaSideResortService service;
    private final ReservationService reservationService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SeaSideResortResponse> createSeaSideResort(@Valid @RequestBody SeaSideResortRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(request));
    }


    @PostMapping("/pay")
    public ResponseEntity<ReservationResponseDto> makeReserve(@Valid @RequestBody ReservationRequestDto request){
        return ResponseEntity.ok(reservationService.save(request));
    }


    @DeleteMapping("/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteByName(@PathVariable  String name){
        service.deleteByName(name);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/create-units/{id}/{start}/{quantity}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Unit>> createUnits(@PathVariable Long id,
                                            @PathVariable int start,
                                            @PathVariable int quantity){
        return ResponseEntity.ok(service.createUnits(id,start,quantity));
    }

    @GetMapping("/units/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Unit>> getUnits(@PathVariable Long id){
        return ResponseEntity.ok(service.getUnits(id));
    }


    @GetMapping("/{name}")
    public ResponseEntity<SeaSideResortResponse> findByName(@PathVariable String name){
        return ResponseEntity.ok(service.findByName(name));
    }

    @GetMapping
    public ResponseEntity<List<SeaSideResortResponse>> findAll(){
        return  ResponseEntity.ok(service.findAll());
    }


    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable  Long id){
        return  ResponseEntity.ok(service.getComments(id));
    }



}
