package com.balneamdp.service;

import com.balneamdp.DTO.request.CommentRequestDto;
import com.balneamdp.DTO.response.CommentResponseDto;
import com.balneamdp.exceptions.ResourseNotFoundException;
import com.balneamdp.mapper.CommentMapper;
import com.balneamdp.models.Comments;
import com.balneamdp.models.SeaSideResort;
import com.balneamdp.repository.CommentsRepository;
import com.balneamdp.repository.SeaSideResortRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentsService {
    private final CommentsRepository commentsRepository;
    private final SeaSideResortRepository seaSideResortRepository;
    private final CommentMapper mapper;

    public CommentResponseDto save(CommentRequestDto request){
        SeaSideResort seaSideResort = seaSideResortRepository.findById(request.getSeaSideResortId())
                .orElseThrow(()-> new ResourseNotFoundException("El balneario no fue encontrado"));
        Comments comment=mapper.toEntity(request);
        comment.setSeaSideResort(seaSideResort);
        return mapper.toDto(commentsRepository.save(comment));
    }

    public List<Comments> getCommentsBySeaSideResort(Long id){
        return commentsRepository.findAll().stream()
                .filter(c->c.getSeaSideResort().getId().equals(id))
                .toList();
    }
}
