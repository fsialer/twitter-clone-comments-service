package com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.repository;

import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.Models.CommentDocument;
import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.Models.CommentPost;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface CommentReactiveMongoRepository extends ReactiveMongoRepository<CommentDocument,String> {

    Flux<CommentDocument> findAllByCommentPost(CommentPost commentPost);
}
