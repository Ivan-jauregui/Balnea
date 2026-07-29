package com.balneamdp.controller;

import com.balneamdp.DTO.request.RowRequest;
import com.balneamdp.models.Row;
import com.balneamdp.service.RowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fila")
@RequiredArgsConstructor
public class RowController {
    private final RowService rowService;

    @PostMapping
    public ResponseEntity<Row> save(@Valid @RequestBody RowRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(rowService.save(request));
    }

    @DeleteMapping("{/id}")
    public ResponseEntity<Void> deleteById(@PathVariable  Long id){
        rowService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{/id}")
    public ResponseEntity<Row> update(@RequestBody RowRequest request,@PathVariable  Long id){
        return ResponseEntity.ok(rowService.update(request,id));
    }
}
