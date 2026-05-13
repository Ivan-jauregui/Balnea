package com.balneamdp.exceptions.handler;

import com.balneamdp.exceptions.RecourseNotFoundException;
import com.balneamdp.exceptions.ressponse.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,HttpServletRequest request){
        Map<String,String> errors=new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));


        ErrorResponseDTO response = new ErrorResponseDTO(
                400,
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Error de validación de los datos enviados",
                request.getServletPath(),
                LocalDateTime.now(),
                errors
        );


        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(RecourseNotFoundException.class)
    private ResponseEntity<ErrorResponseDTO> handlerRecourseNotFoundException(RecourseNotFoundException ex,HttpServletRequest request){
        ErrorResponseDTO response = new ErrorResponseDTO(
                404,
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getServletPath(),
                LocalDateTime.now(),
                Collections.emptyMap()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

    }

}
