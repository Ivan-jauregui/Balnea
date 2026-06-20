package com.balneamdp.controller;

import com.balneamdp.DTO.request.CommentRequestDto;
import com.balneamdp.DTO.response.CommentResponseDto;
import com.balneamdp.models.Comments;
import com.balneamdp.service.CommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentsController {
    private final CommentsService service;

    @PostMapping
    private CommentResponseDto save(@RequestBody CommentRequestDto request){
        return service.save(request);
    }
}
