package com.fernando.ms.comments.app.infraestructure.adapter.input.rest;

import com.fernando.ms.comments.app.application.ports.input.CommentInputPort;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.mapper.CommentRestMapper;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.response.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentRestAdapter {
    private final CommentInputPort commentInputPort;
    private final CommentRestMapper commentRestMapper;

    @GetMapping
    public Flux<CommentResponse> findAll(){
        return  commentRestMapper.toCommentsResponse(commentInputPort.findAll());
    }
}
