package com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.repository;

import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.model.CommentDocument;
import reactor.core.publisher.Flux;

public interface CommentRepositoryCustom {
    Flux<CommentDocument> findAllByPostId(String postId,int page,int size);
}
