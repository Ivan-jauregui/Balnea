package com.balneamdp.service;

import com.balneamdp.DTO.request.RowRequest;
import com.balneamdp.exceptions.ResourseNotFoundException;
import com.balneamdp.mapper.RowMapper;
import com.balneamdp.models.BeachTent;
import com.balneamdp.models.Row;
import com.balneamdp.repository.BeachTentRepository;
import com.balneamdp.repository.RowRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RowService {
    private final RowRepository rowRepository;
    private final BeachTentRepository beachTentRepository;
    private final RowMapper mapper;

    @Transactional
    public Row save(RowRequest request){
        Row row = mapper.toEntity(request);
        for (int i = request.getFirstBeachTent(); i <= request.getLastBeachTent(); i++){
            BeachTent newBeachTent =BeachTent.builder()
            .number(i)
            .build();
            row.addBeachTent(newBeachTent);
        }
        return rowRepository.save(row);
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
