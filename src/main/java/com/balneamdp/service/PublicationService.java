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
        SeaSideResort seaSideResort = seaSideResortRepository.findById(request.getSeaSideResort)
                .orElseThrow(()->new ResourseNotFoundException("Balneario no encontrado"));

        Publication publication = publicationMapper.toEntity(request);

        publication.setSeaSideResort(seaSideResort);

        return publicationMapper.toDto(publication);
    }

    public List<PublicationResponseDto> findAllPublicationBySeaSideResort(Long seaSideResortId){
        return publicationRepository.findBySeaSideResortId(seaSideResortId).stream()
                .map(publicationMapper::toDto)
                .orElseThrow(() -> new ResourseNotFoundException("Balneario no encontrado"));
    }

    public PublicationResponseDto findById(Long id){
        return publicationRepository.findById(id);
    }

    public void deleteById(Long id){
        publicationRepository.deleteById(id);
    }


}
