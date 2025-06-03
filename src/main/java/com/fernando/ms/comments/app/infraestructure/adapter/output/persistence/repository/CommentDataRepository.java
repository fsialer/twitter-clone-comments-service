package com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.repository;

import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.model.CommentDataDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CommentDataRepository extends ReactiveMongoRepository<CommentDataDocument,String>, CommentDataRepositoryCustom {
    Mono<Boolean> existsByCommentIdAndUserId(String commentId, String userId);
}
