package com.balneamdp.controller;

import com.balneamdp.DTO.SeaSideResortRequest;
import com.balneamdp.DTO.SeaSideResortResponse;
import com.balneamdp.DTO.request.ReservationRequestDto;
import com.balneamdp.DTO.request.SeaSideResortFilterDto;
import com.balneamdp.DTO.response.BeachTentResponseDto;
import com.balneamdp.DTO.response.CommentResponseDto;
import com.balneamdp.DTO.response.PublicationResponseDto;
import com.balneamdp.DTO.response.ReservationResponseDto;
import com.balneamdp.models.*;
import com.balneamdp.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.stream.events.Comment;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/balnearios")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class SeaSideResortController {
    private final SeaSideResortService service;
    private final ReservationService reservationService;
    private final ArchiveValidatorService archiveValidatorService;
    private final CloudinaryService cloudinaryService;
    private final PublicationService publicationService;

    @PostMapping
    //@PreAuthorize("hasRole('ADMIN')")
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


    @GetMapping
            public ResponseEntity<Page<SeaSideResortResponse>> getAll(
                    @ModelAttribute SeaSideResortFilterDto filter,
                    @RequestParam(defaultValue = "0") int page,
                    @RequestParam(defaultValue = "10") int size
            ) {
        return ResponseEntity.ok(service.getSeaSideResorts(filter, page, size));
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<SeaSideResortResponse> findByName(@RequestParam String name){
        return ResponseEntity.ok(service.findByName(name));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeaSideResortResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    /*@GetMapping
    public ResponseEntity<List<SeaSideResortResponse>> findAll(){
        return  ResponseEntity.ok(service.findAll());
    }*/


    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable  Long id){
        return  ResponseEntity.ok(service.getComments(id));
    }

    @PostMapping("/{id}/image")
    public ResponseEntity<?> uploadImage(@PathVariable Long id, @RequestParam("archive") MultipartFile archive){

        try{
            archiveValidatorService.valditeFile(archive);
            Map<String,Object> response= cloudinaryService.uploadImage(archive,"seaSideResort");

            String urlImage = (String) response.get("secure_url");
            String publicId = (String) response.get("public_id");

            SeaSideResort updatedResort = service.updateImage(id,urlImage,publicId);

            return  ResponseEntity.ok(updatedResort);

        } catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); // ✅ 400 con mensaje
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar el archivo");
        }
    }

    @GetMapping("amenities/{id}")
    public ResponseEntity<List<Amenity>> getServices(@PathVariable Long id){
        return ResponseEntity.ok(service.getServices(id));
    }

    public ResponseEntity<List<PublicationResponseDto>> findAllPublicationBySeaSideResort(@PathVariable Long seaSideResortId){
        return  ResponseEntity.ok(publicationService.findAllPublicationBySeaSideResort(seaSideResortId));
    }

}
