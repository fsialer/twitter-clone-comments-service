package com.fernando.ms.comments.app.application.ports.input;

import com.fernando.ms.comments.app.domain.models.CommentData;
import reactor.core.publisher.Mono;

public interface CommentDataInputPort {
    Mono<Void> save(CommentData commentData);
    Mono<Void> delete(String id);
}
