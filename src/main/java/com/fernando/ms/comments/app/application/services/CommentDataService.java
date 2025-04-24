package com.fernando.ms.comments.app.application.services;

import com.fernando.ms.comments.app.application.ports.input.CommentDataInputPort;
import com.fernando.ms.comments.app.application.ports.output.CommentDataPersistencePort;
import com.fernando.ms.comments.app.application.ports.output.CommentPersistencePort;
import com.fernando.ms.comments.app.domain.exception.CommentDataNotFoundException;
import com.fernando.ms.comments.app.domain.exception.CommentNotFoundException;
import com.fernando.ms.comments.app.domain.exception.CommentRuleException;
import com.fernando.ms.comments.app.domain.models.CommentData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CommentDataService implements CommentDataInputPort {
    private final CommentDataPersistencePort commentDataPersistencePort;
    private final CommentPersistencePort commentPersistencePort;

    @Override
    public Mono<Void> save(CommentData commentData) {
        return commentPersistencePort.findById(commentData.getCommentId())
                .switchIfEmpty(Mono.error(CommentNotFoundException::new))
                .flatMap(commentInfo->commentDataPersistencePort.verifyCommentData(commentData.getCommentId(),commentData.getUserId())
                        .filter(Boolean.FALSE::equals)
                        .switchIfEmpty(Mono.error(new CommentRuleException("CommentData already exists")))
                        .flatMap(verify->commentDataPersistencePort.save(commentData)));
    }

    @Override
    public Mono<Void> delete(String id) {
        return commentDataPersistencePort.findById(id)
                .switchIfEmpty(Mono.error(CommentDataNotFoundException::new))
                .flatMap(postData->commentDataPersistencePort.delete(id));
    }


}
