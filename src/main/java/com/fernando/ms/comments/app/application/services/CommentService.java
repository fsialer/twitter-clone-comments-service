package com.fernando.ms.comments.app.application.services;

import com.fernando.ms.comments.app.application.ports.input.CommentInputPort;
import com.fernando.ms.comments.app.application.ports.output.CommentPersistencePort;
import com.fernando.ms.comments.app.domain.exception.CommentNotFoundException;
import com.fernando.ms.comments.app.domain.models.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CommentService implements CommentInputPort {
    private final CommentPersistencePort commentPersistencePort;
    @Override
    public Flux<Comment> findAll() {
        return commentPersistencePort.findAll();
    }

    @Override
    public Mono<Comment> findById(String id) {
        return commentPersistencePort.findById(id)
                .switchIfEmpty(Mono.error(CommentNotFoundException::new));
    }
}
