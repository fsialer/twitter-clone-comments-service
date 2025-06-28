package com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.repository;

import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.model.CommentDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CommentRepository extends ReactiveMongoRepository<CommentDocument,String>,CommentRepositoryCustom {
    Mono<Boolean> existsByCommentIdAndUserId(String commentId, String userId);

}
