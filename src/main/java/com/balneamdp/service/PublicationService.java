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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicationService {
    private final PublicationRepository publicationRepository;
    private final SeaSideResortRepository seaSideResortRepository;

    private final PublicationMapper publicationMapper;

    public PublicationResponseDto save(PublicationRequestDto request){
        SeaSideResort seaSideResort = seaSideResortRepository.findById(request.getSeaSideResortId())
                .orElseThrow(()->new ResourseNotFoundException("Balneario no encontrado"));

        Publication publication = publicationMapper.toEntity(request);

        publication.setSeaSideResort(seaSideResort);

        return publicationMapper.toDto(publication);
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
