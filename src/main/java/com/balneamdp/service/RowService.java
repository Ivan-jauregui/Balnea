package com.balneamdp.service;

import com.balneamdp.DTO.request.RowRequest;
import com.balneamdp.exceptions.ResourseNotFoundException;
import com.balneamdp.mapper.RowMapper;
import com.balneamdp.models.Row;
import com.balneamdp.repository.RowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RowService {
    private final RowRepository rowRepository;
    private final RowMapper mapper;

    public Row save(RowRequest request){
        return rowRepository.save(mapper.toEntity(request));
    }

    public void deleteById(Long id){
        rowRepository.deleteById(id);
    }

    public Row update(RowRequest request,Long id){
         Row rowFound = rowRepository.findById(id)
                 .orElseThrow(()->new ResourseNotFoundException("Fila no encontrada"));

         rowFound.setNumber(request.getNumber());
         rowFound.setTag(request.getTag());

         return rowFound;
    }


}
