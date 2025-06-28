package com.fernando.ms.comments.app.application.ports.output;

import com.fernando.ms.comments.app.domain.models.Comment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CommentPersistencePort {
    Flux<Comment> findAll();
    Mono<Comment> findById(String id);
    Mono<Comment> save(Comment comment);
    Mono<Void> delete(String id);
    Flux<Comment> findAllByPostId(String postId,int page,int size);
    Mono<Boolean> verifyById(String id);
    Mono<Long> countCommentByPostId(String postId);
    Mono<Boolean> verifyCommentByUserId(String commentId, String userId);
}
