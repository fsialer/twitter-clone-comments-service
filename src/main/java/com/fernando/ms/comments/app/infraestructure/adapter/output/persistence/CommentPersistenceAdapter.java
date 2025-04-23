package com.fernando.ms.comments.app.infraestructure.adapter.output.persistence;

import com.fernando.ms.comments.app.application.ports.output.CommentPersistencePort;
import com.fernando.ms.comments.app.domain.models.Comment;
import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.mapper.CommentPersistenceMapper;
import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CommentPersistenceAdapter implements CommentPersistencePort {
    private final CommentRepository commentRepository;
    private final CommentPersistenceMapper commentPersistenceMapper;

    @Override
    public Flux<Comment> findAll() {
        return commentPersistenceMapper.toComments(commentRepository.findAll());
    }

    @Override
    public Mono<Comment> findById(String id) {
        return commentRepository.findById(id).map(commentPersistenceMapper::toComment);
    }

    @Override
    public Mono<Comment> save(Comment comment) {
        return commentPersistenceMapper.toComment(commentRepository.save(commentPersistenceMapper.toCommentDocument(comment)));
    }

    @Override
    public Mono<Void> delete(String id) {
        return commentRepository.deleteById(id);
    }

    @Override
    public Flux<Comment> findAllByPostId(String postId) {
        return commentPersistenceMapper.toComments(commentRepository.findAllByPostId(postId));
    }

    @Override
    public Mono<Boolean> verifyById(String id) {
        return commentRepository.existsById(id);
    }
}
