package com.fernando.ms.comments.app.infraestructure.adapter.input.rest;

import com.fernando.ms.comments.app.application.ports.input.CommentInputPort;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.mapper.CommentRestMapper;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.response.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

    @GetMapping("/{id}")
    public Mono<ResponseEntity<CommentResponse>> findById(@PathVariable("id") String id){
        return commentInputPort.findById(id)
                .flatMap(comment -> {
                    return Mono.just(ResponseEntity.ok().body(commentRestMapper.toCommentResponse(comment)));
                });
    }
}
