package com.fernando.ms.comments.app.application.ports.output;

import com.fernando.ms.comments.app.domain.models.Comment;
import reactor.core.publisher.Flux;

public interface CommentPersistencePort {
    Flux<Comment> findAll();
}
