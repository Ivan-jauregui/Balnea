package com.balneamdp.service;

import com.balneamdp.DTO.SeaSideResortRequest;
import com.balneamdp.DTO.SeaSideResortResponse;
import com.balneamdp.DTO.response.CommentResponseDto;
import com.balneamdp.DTO.response.UserResponseDto;
import com.balneamdp.exceptions.ResourseNotFoundException;
import com.balneamdp.mapper.CommentMapper;
import com.balneamdp.mapper.MapperSeaSideResort;
import com.balneamdp.mapper.UserMapper;
import com.balneamdp.models.*;
import com.balneamdp.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SeaSideResortService {

    private final SeaSideResortRepository seaSideResortRepository;
    private final AmenityRepository amenityRepository;
    private final UserRepository userRepository;
    private final CommentsRepository commentsRepository;
    private final UnitRepository unitRepository;
    private final MapperSeaSideResort mapper;
    private final UserMapper userMapper;
    private final CommentMapper commentMapper;

    public SeaSideResortResponse save(SeaSideResortRequest request) {
        User owner= userRepository.findById(request.getOwnerId())
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
        resort.setCreated_at(LocalDateTime.now());

        SeaSideResort savedResort = seaSideResortRepository.save(resort);
        return mapper.toDto(savedResort);
    }


    public void deleteByName(String name) {
        SeaSideResort resort = seaSideResortRepository.findByName(name)
                .orElseThrow(() -> new ResourseNotFoundException("Balneario con nombre " + name + " no encontrado"));

        seaSideResortRepository.delete(resort);
    }


    public SeaSideResortResponse findByName(String name) {
        return seaSideResortRepository.findByName(name)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourseNotFoundException("Balneario no encontrado"));
    }


    public List<SeaSideResortResponse> findAll() {
        return seaSideResortRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    public List<UserResponseDto> getClients(String name){
         SeaSideResort seaSideResort  = seaSideResortRepository.findByName(name)
                .orElseThrow(()->new ResourseNotFoundException("Balneario no fue encontrado"));

         return  seaSideResort.getClients().stream()
                 .map(userMapper::toDto)
                 .toList();
    }

    public List<CommentResponseDto> getComments(Long seaSideResortId){
        return commentsRepository.findBySeaSideResortId(seaSideResortId).stream()
                .map(commentMapper::toDto)
                .toList();
    }

    @Transactional
    public List<Unit> createUnits(Long id,int start,int quantity){
        for(int i=0;i<quantity;i++){
            Unit newUnit=new Unit();
            newUnit.setNumber(start + i);
            newUnit.setInMaintenance(false);
            newUnit.setPayState(PayState.PENDIENTE);
            newUnit.setSeaSideResort(seaSideResortRepository.findById(id).get());
            unitRepository.save(newUnit);
        }
        return getUnits(id);
    }

    public List<Unit> getUnits(Long id){
        return unitRepository.findAllBySeaSideResortId(id);
    }


    public List<Unit> getAllPendingUnits(Long id) {
        SeaSideResort seaSideResort = seaSideResortRepository.findById(id)
                .orElseThrow(() -> new ResourseNotFoundException("Balneario no fue encontrado"));

        return seaSideResort.getUnits().stream()
                .filter(u -> u.getPayState() == PayState.PENDIENTE)
                .toList();
    }
    public List<Unit> getAllPaidUnits(Long id){
        SeaSideResort seaSideResort = seaSideResortRepository.findById(id)
                .orElseThrow(() -> new ResourseNotFoundException("Balneario no fue encontrado"));

        return seaSideResort.getUnits().stream()
                .filter(u-> u.getPayState()== PayState.SEÑADO)
                .toList();
    }
    public List<Unit> getAllMarkedUnits(Long id){
        SeaSideResort seaSideResort = seaSideResortRepository.findById(id)
                .orElseThrow(() -> new ResourseNotFoundException("Balneario no fue encontrado"));

        return seaSideResort.getUnits().stream()
                .filter(u-> u.getPayState()== PayState.PAGADO)
                .toList();
    }

}