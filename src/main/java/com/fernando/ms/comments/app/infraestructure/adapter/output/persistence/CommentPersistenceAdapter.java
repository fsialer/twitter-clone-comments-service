package com.fernando.ms.comments.app.infraestructure.adapter.output.persistence;

import com.fernando.ms.comments.app.application.ports.output.CommentPersistencePort;
import com.fernando.ms.comments.app.domain.models.Comment;
import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.mapper.CommentPersistenceMapper;
import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.repository.CommentReactiveMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CommentPersistenceAdapter implements CommentPersistencePort {
    private final CommentReactiveMongoRepository commentReactiveMongoRepository;
    private final CommentPersistenceMapper commentPersistenceMapper;

    @Override
    public Flux<Comment> findAll() {
        return commentPersistenceMapper.toComments(commentReactiveMongoRepository.findAll());
    }

    @Override
    public Mono<Comment> findById(String id) {
        return commentReactiveMongoRepository.findById(id).map(commentPersistenceMapper::toComment);
    }
}
