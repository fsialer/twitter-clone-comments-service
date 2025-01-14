package com.fernando.ms.comments.app.application.ports.input;

import com.fernando.ms.comments.app.domain.models.Comment;
import reactor.core.publisher.Flux;

public interface CommentInputPort {
    Flux<Comment> findAll();
}
