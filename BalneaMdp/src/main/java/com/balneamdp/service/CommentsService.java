package com.balneamdp.service;

import com.balneamdp.exceptions.ResourseNotFoundException;
import com.balneamdp.models.Comments;
import com.balneamdp.models.SeaSideResort;
import com.balneamdp.repository.CommentsRepository;
import com.balneamdp.repository.SeaSideResortRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentsService {
    private final CommentsRepository commentsRepository;
    private final SeaSideResortRepository seaSideResortRepository;

    public Comments save(Comments c){
        SeaSideResort seaSideResort = seaSideResortRepository.findById(c.getSeaSideResort().getId())
                .orElseThrow(()-> new ResourseNotFoundException("El balneario no fue encontrado"));
        return commentsRepository.save(c);
    }
}
