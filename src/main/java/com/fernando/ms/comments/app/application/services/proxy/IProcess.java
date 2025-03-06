package com.fernando.ms.comments.app.application.services.proxy;

import com.fernando.ms.comments.app.domain.models.Comment;
import reactor.core.publisher.Mono;

public interface IProcess {
    Mono<Comment> doProcess(Comment comment);
}
