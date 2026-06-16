package com.balneamdp.controller;

import com.balneamdp.models.Comments;
import com.balneamdp.service.CommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.stream.events.Comment;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentsController {
    private final CommentsService service;

    @PostMapping
    private Comments save(@RequestBody Comments c){
        return service.save(c);
    }
}
