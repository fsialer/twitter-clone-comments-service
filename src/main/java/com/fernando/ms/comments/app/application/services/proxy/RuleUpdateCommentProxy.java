package com.fernando.ms.comments.app.application.services.proxy;

import com.fernando.ms.comments.app.application.ports.output.CommentPersistencePort;
import com.fernando.ms.comments.app.domain.exception.CommentNotFoundException;
import com.fernando.ms.comments.app.domain.models.Comment;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class RuleUpdateCommentProxy implements IProcess{
    private final CommentPersistencePort commentPersistencePort;
    private final String id;

    @Override
    public Mono<Comment> doProcess(Comment comment) {
        return commentPersistencePort.findById(id)
                .switchIfEmpty(Mono.error(CommentNotFoundException::new))
                .flatMap(commentUpdated->{
                    commentUpdated.setContent(comment.getContent());
                    return Mono.just(commentUpdated);
                });
    }
}
