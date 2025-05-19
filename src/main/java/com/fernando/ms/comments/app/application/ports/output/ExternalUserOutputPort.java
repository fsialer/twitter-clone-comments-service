package com.fernando.ms.comments.app.application.ports.output;

import com.fernando.ms.comments.app.domain.models.Author;
import reactor.core.publisher.Mono;

public interface ExternalUserOutputPort {
    Mono<Author> findAuthorByUserId(String userId);
}
