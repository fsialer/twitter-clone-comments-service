package com.fernando.ms.comments.app.application.services.proxy;

import com.fernando.ms.comments.app.application.ports.output.ExternalPostOutputPort;
import com.fernando.ms.comments.app.application.ports.output.ExternalUserOutputPort;
import com.fernando.ms.comments.app.domain.exception.PostNotFoundException;
import com.fernando.ms.comments.app.domain.exception.UserNotFoundException;
import com.fernando.ms.comments.app.domain.models.Comment;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class RuleSaveCommentProxy implements IProcess{
    private final ExternalUserOutputPort externalUserOutputPort;
    private final ExternalPostOutputPort externalPostOutputPort;
    @Override
    public Mono<Comment> doProcess(Comment comment) {
        return externalUserOutputPort.verify(comment.getUser().getId())
                .filter(Boolean.TRUE::equals)
                .switchIfEmpty(Mono.error(new UserNotFoundException()))
                .flatMap(userExists->
                    externalPostOutputPort.verify(comment.getPost().getId())
                            .filter(Boolean.TRUE::equals)
                            .switchIfEmpty(Mono.error(new PostNotFoundException()))
                            .thenReturn(comment)
                ).log();
    }
}
