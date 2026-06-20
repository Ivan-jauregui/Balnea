package com.balneamdp.DTO.response;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {
    String text;
    LocalDate created_at;
}
