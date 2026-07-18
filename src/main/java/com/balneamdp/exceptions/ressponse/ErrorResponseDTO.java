package com.balneamdp.exceptions.ressponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;


@Getter  @Setter  @NoArgsConstructor  @AllArgsConstructor  @Builder
public class ErrorResponseDTO {
    private int status;
    private String error;
    private String mesage;
    private String path;
    private LocalDateTime timestamp;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String,String> validations;
}
