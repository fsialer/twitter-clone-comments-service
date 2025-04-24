package com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.repository;

import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.Models.CommentDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface CommentRepository extends ReactiveMongoRepository<CommentDocument,String> {

    Flux<CommentDocument> findAllByPostId(String postId);
}
