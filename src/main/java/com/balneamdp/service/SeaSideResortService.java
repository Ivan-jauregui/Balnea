package com.balneamdp.service;

import com.balneamdp.DTO.SeaSideResortRequest;
import com.balneamdp.DTO.SeaSideResortResponse;
import com.balneamdp.DTO.request.RateSeaSideResortRequest;
import com.balneamdp.DTO.request.RowRequest;
import com.balneamdp.DTO.request.SeaSideResortFilterDto;
import com.balneamdp.DTO.response.BeachTentResponseDto;
import com.balneamdp.DTO.response.CommentResponseDto;
import com.balneamdp.DTO.response.UserResponseDto;
import org.springframework.transaction.annotation.Transactional;
import com.balneamdp.exceptions.ResourseNotFoundException;
import com.balneamdp.mapper.*;
import com.balneamdp.models.*;
import com.balneamdp.repository.*;
import com.balneamdp.repository.specification.SeaSideResortSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Page;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SeaSideResortService {

    private final SeaSideResortRepository seaSideResortRepository;
    private final AmenityRepository amenityRepository;
    private final BeachTentRepository beachTentRepository;

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final CommentsRepository commentsRepository;

    private final MapperSeaSideResort mapper;
    private final UserMapper userMapper;
    private final CommentMapper commentMapper;
    private final RowMapper rowMapper;
    private final RateMapper rateMapper;
    private final BeachTentMapper beachTentMapper;

    @Transactional
    public SeaSideResortResponse save(SeaSideResortRequest request) {
        User owner = userRepository.findById(request.getOwnerId())
                .orElseThrow(()-> new ResourseNotFoundException("El dueño no fue encontrado"));

        Set<Amenity> amenities = new HashSet<>(amenityRepository.findAllById(request.getAmenities()));

        if (amenities.size() != request.getAmenities().size()) {

            List<Long> foundIds = amenities.stream().map(Amenity::getId).toList();
            List<Long> missingIds = request.getAmenities().stream()
                    .filter(id -> !foundIds.contains(id))
                    .toList();

            throw new ResourseNotFoundException("Servicio inexistente: " + missingIds);
        }

        SeaSideResort resort = mapper.toEntity(request, amenities,owner);

        for (RowRequest row:request.getRows()){
            Row newRow = rowMapper.toEntity(row);
            resort.addRow(newRow);
        }
        for (RateSeaSideResortRequest rate:request.getRates()){
            RateSeaSideResort newRate = rateMapper.toEntity(rate);
            resort.addRate(newRate);
        }

        SeaSideResort savedResort = seaSideResortRepository.save(resort);

        SeaSideResortResponse seaSideResortResponse = mapper.toDto(savedResort);
        return seaSideResortResponse;
    }


    @Transactional
    public void deleteByName(String name) {
        SeaSideResort resort = seaSideResortRepository.findByName(name)
                .orElseThrow(() -> new ResourseNotFoundException("Balneario con nombre " + name + " no encontrado"));

        seaSideResortRepository.delete(resort);
    }

    public Page<SeaSideResortResponse> getSeaSideResorts(SeaSideResortFilterDto filter, int page, int size) {
        if (filter == null) {
            filter = new SeaSideResortFilterDto();
        }

        Specification<SeaSideResort> spec = SeaSideResortSpecification.byFilter(filter);
        Pageable pageable = PageRequest.of(page, size);

        return seaSideResortRepository.findAll(spec, pageable).map(mapper::toDto);
    }



    public SeaSideResortResponse findByName(String name) {
        return seaSideResortRepository.findByName(name)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourseNotFoundException("Balneario no encontrado"));
    }

    public SeaSideResortResponse findById(Long id) {
        return seaSideResortRepository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourseNotFoundException("Balneario no encontrado"));
    }


    public List<CommentResponseDto> getComments(Long seaSideResortId){
        return commentsRepository.findBySeaSideResortId(seaSideResortId).stream()
                .map(commentMapper::toDto)
                .toList();
    }


    @Transactional
    public SeaSideResort updateImage(Long id,String imageUrl,String publicId){
        SeaSideResort seaSideResort = seaSideResortRepository.findById(id)
                .orElseThrow(() -> new ResourseNotFoundException("Balneario no fue encontrado"));

        seaSideResort.setImageUrl(imageUrl);
        seaSideResort.setImagePublicId(publicId);

        return seaSideResortRepository.save(seaSideResort);
    }

    public List<Amenity> getServices(Long id){
        SeaSideResort seaSideResort = seaSideResortRepository.findById(id)
                .orElseThrow(() -> new ResourseNotFoundException("Balneario no fue encontrado"));

        return seaSideResort.getAmenities().stream().toList();
    }
}