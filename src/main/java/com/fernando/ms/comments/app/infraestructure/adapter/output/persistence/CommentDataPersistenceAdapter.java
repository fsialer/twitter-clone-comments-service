package com.fernando.ms.comments.app.infraestructure.adapter.output.persistence;

import com.fernando.ms.comments.app.application.ports.output.CommentDataPersistencePort;
import com.fernando.ms.comments.app.domain.models.CommentData;
import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.mapper.CommentDataPersistenceMapper;
import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.repository.CommentDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CommentDataPersistenceAdapter implements CommentDataPersistencePort {
    private final CommentDataRepository commentDataRepository;
    private final CommentDataPersistenceMapper commentDataPersistenceMapper;
    @Override
    public Mono<Void> save(CommentData commentData) {
        return commentDataPersistenceMapper.toCommentData(commentDataRepository.save(commentDataPersistenceMapper.toCommentDataDocument(commentData))).then();
    }

    @Override
    public Mono<Boolean> verifyCommentData(String commentId, String userId) {
        return commentDataRepository.existsByCommentIdAndUserId(commentId,userId);
    }

    @Override
    public Mono<CommentData> findById(String id) {
        return commentDataPersistenceMapper.toCommentData(commentDataRepository.findById(id));
    }

    @Override
    public Mono<Void> delete(String id) {
        return commentDataRepository.deleteById(id).then();
    }

    @Override
    public Mono<Long> countCommentDataByComment(String commentId) {
        return commentDataRepository.countCommentDataByCommentId(commentId);
    }

    @Override
    public Mono<CommentData> findByCommentIdAndUserId(String commentId, String userId) {
        return commentDataPersistenceMapper.toCommentData(commentDataRepository.findByCommentIdAndUserId(commentId,userId));
    }

}
