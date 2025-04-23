package com.fernando.ms.comments.app.infraestructure.adapter.input.rest;

import com.fernando.ms.comments.app.application.ports.input.CommentInputPort;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.mapper.CommentRestMapper;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.request.CreateCommentRequest;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.request.UpdateCommentRequest;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.response.CommentResponse;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.response.CommentUserResponse;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.response.ExistsCommentResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/comments")
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

    @PostMapping
    public Mono<ResponseEntity<CommentResponse>> save(@RequestHeader("X-User-Id") Long userId,
                                                      @Valid @RequestBody CreateCommentRequest rq){
        return commentInputPort.save(commentRestMapper.toComment(userId,rq))
                .flatMap(comment -> {
                    String location = "/comments/".concat(comment.getId());
                    return Mono.just(ResponseEntity.created(URI.create(location)).body(commentRestMapper.toCommentResponse(comment)));
                });
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<CommentResponse>> update(@PathVariable("id") String id,@Valid @RequestBody UpdateCommentRequest rq){
        return commentInputPort.update(id,commentRestMapper.toComment(rq))
                .flatMap(comment->{
                    return Mono.just(ResponseEntity.ok().body(commentRestMapper.toCommentResponse(comment)));
                });
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable("id") String id){
        return commentInputPort.delete(id);
    }

    @GetMapping("/{idPost}/post")
    public Flux<CommentUserResponse> findAllByPostId(@PathVariable("idPost") String postId){
        return commentRestMapper.toCommentsUserResponse(commentInputPort.findAllByPostId(postId));
    }

    @GetMapping("/{id}/verify")
    public Mono<ResponseEntity<ExistsCommentResponse>> verify(@PathVariable("id") String id){
        return commentInputPort.verifyById(id)
                .flatMap(exists->{
                    return Mono.just(ResponseEntity.ok().body(commentRestMapper.toExistsCommentResponse(exists)));
                });
    }
}
