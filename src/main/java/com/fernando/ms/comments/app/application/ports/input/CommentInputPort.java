package com.fernando.ms.comments.app.application.ports.input;

import com.fernando.ms.comments.app.domain.models.Comment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CommentInputPort {
    Flux<Comment> findAll();

    Mono<Comment> findById(String id);

    Mono<Comment> save(Comment comment);

    Mono<Comment> update(String id, Comment comment);

    Mono<Void> delete(String id);

    Flux<Comment> findAllByPost(String postId);

    Mono<Boolean> verifyById(String id);
}
