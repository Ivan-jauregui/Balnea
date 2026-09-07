package com.balneamdp.service;

import com.balneamdp.models.Publication;
import com.balneamdp.DTO.response.PublicationResponseDto;
import com.balneamdp.DTO.request.PublicationRequestDto;
import com.balneamdp.exceptions.ResourseNotFoundException;
import com.balneamdp.mapper.PublicationMapper;
import com.balneamdp.models.SeaSideResort;
import com.balneamdp.repository.PublicationRepository;
import com.balneamdp.repository.SeaSideResortRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PublicationService {
    private final PublicationRepository publicationRepository;
    private final SeaSideResortRepository seaSideResortRepository;
    private final CloudinaryService cloudinaryService;
    private final ArchiveValidatorService archiveValidatorService;

    private final PublicationMapper publicationMapper;

    public PublicationResponseDto save(PublicationRequestDto request, Long seaSideResortId) {
        SeaSideResort seaSideResort = seaSideResortRepository.findById(seaSideResortId)
                .orElseThrow(() -> new ResourseNotFoundException("Balneario no encontrado"));

        try {
            archiveValidatorService.validateFile(request.getImage());
            Map<String, Object> response = cloudinaryService.uploadImage(request.getImage(), "seaSideResortPublication");

            String urlImage = (String) response.get("secure_url");
            String publicId = (String) response.get("public_id");

            Publication publication = publicationMapper.toEntity(request);
            publication.setImageUrl(urlImage);
            publication.setImagePublicId(publicId);
            publication.setSeaSideResort(seaSideResort);

            Publication savedPublication = publicationRepository.save(publication);

            return publicationMapper.toDto(savedPublication);
        } catch (IOException e) {
            throw new RuntimeException("Error al procesar el archivo en Cloudinary", e);
        }
    }


    public List<PublicationResponseDto> findAllPublicationBySeaSideResort(Long seaSideResortId) {
        List<Publication> publications = publicationRepository.findBySeaSideResortId(seaSideResortId);

        if (publications.isEmpty()) {
            throw new ResourseNotFoundException("No se encontraron publicaciones para el balneario con ID: " + seaSideResortId);
        }

        return publications.stream()
                .map(publicationMapper::toDto)
                .toList();
    }

    public PublicationResponseDto findById(Long id) {

        Publication publication = publicationRepository.findById(id)
                .orElseThrow(() -> new ResourseNotFoundException("Publicación no encontrada con ID: " + id));

        return publicationMapper.toDto(publication);
    }

    public void deleteById(Long id){
        publicationRepository.deleteById(id);
    }


}
