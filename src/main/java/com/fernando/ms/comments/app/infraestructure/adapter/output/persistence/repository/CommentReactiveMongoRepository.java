package com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.repository;

import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.Models.CommentDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CommentReactiveMongoRepository extends ReactiveMongoRepository<CommentDocument,String> {
}
