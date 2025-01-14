package com.fernando.ms.comments.app.application.ports.input;

import com.fernando.ms.comments.app.domain.models.Comment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CommentInputPort {
    Flux<Comment> findAll();
    Mono<Comment> findById(String id);
    Mono<Comment> save(Comment comment);
}
