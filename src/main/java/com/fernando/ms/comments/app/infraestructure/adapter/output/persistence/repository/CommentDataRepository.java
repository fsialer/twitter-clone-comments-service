package com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.repository;

import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.models.CommentDataDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CommentDataRepository extends ReactiveMongoRepository<CommentDataDocument,String> {
    Mono<Boolean> existsByCommentIdAndUserId(String commentId, String userId);
}
