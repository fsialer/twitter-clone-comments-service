package com.fernando.ms.comments.app.application.services;

import com.fernando.ms.comments.app.application.ports.input.CommentInputPort;
import com.fernando.ms.comments.app.application.ports.output.CommentPersistencePort;
import com.fernando.ms.comments.app.domain.models.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class CommentService implements CommentInputPort {
    private final CommentPersistencePort commentPersistencePort;
    @Override
    public Flux<Comment> findAll() {
        return commentPersistencePort.findAll();
    }
}
