package com.fernando.ms.comments.app.application.ports.output;

import com.fernando.ms.comments.app.domain.models.CommentData;
import reactor.core.publisher.Mono;

public interface CommentDataPersistencePort {
    Mono<Void> save(CommentData commentData);
    Mono<Boolean> verifyCommentData(String commentId,String userId);
    Mono<CommentData> findById(String id);
    Mono<Void> delete(String id);
    Mono<Long> countCommentDataByComment(String commentId);
}
