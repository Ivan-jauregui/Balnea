package com.balneamdp.service;

import com.balneamdp.models.Amenity;
import com.balneamdp.repository.AmenityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AmenityService {
    private final AmenityRepository repository;

    public Amenity save(Amenity amenity){
        return repository.save(amenity);
    }

    public void deleteByName(String name){
        repository.deleteByName(name);
    }

    public Amenity findByName(String name){
        return repository.findByName(name);
    }



    public List<Amenity> findAll(){
        return repository.findAll();
    }

    public Amenity update(Long id , Amenity amenity){
        Amenity AmenityDB=repository.findById(id)
                .orElseThrow(()->new RuntimeException("Servicio no encontrado"));

        AmenityDB.setName(amenity.getName());
        return repository.save(amenity);
    }
}
