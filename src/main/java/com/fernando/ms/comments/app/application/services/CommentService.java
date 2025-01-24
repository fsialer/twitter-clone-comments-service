package com.fernando.ms.comments.app.application.services;

import com.fernando.ms.comments.app.application.ports.input.CommentInputPort;
import com.fernando.ms.comments.app.application.ports.output.CommentPersistencePort;
import com.fernando.ms.comments.app.application.ports.output.ExternalPostOutputPort;
import com.fernando.ms.comments.app.application.ports.output.ExternalUserOutputPort;
import com.fernando.ms.comments.app.domain.exception.CommentNotFoundException;
import com.fernando.ms.comments.app.domain.exception.PostNotFoundException;
import com.fernando.ms.comments.app.domain.exception.UserNotFoundException;
import com.fernando.ms.comments.app.domain.models.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CommentService implements CommentInputPort {
    private final CommentPersistencePort commentPersistencePort;
    private final ExternalUserOutputPort externalUserOutputPort;
    private final ExternalPostOutputPort externalPostOutputPort;

    @Override
    public Flux<Comment> findAll() {
        return commentPersistencePort.findAll();
    }

    @Override
    public Mono<Comment> findById(String id) {
        return commentPersistencePort.findById(id)
                .switchIfEmpty(Mono.error(CommentNotFoundException::new));
    }

    @Override
    public Mono<Comment> save(Comment comment) {
        return externalUserOutputPort.verify(comment.getUser().getId())
                .flatMap(userExists->{
                    if(Boolean.FALSE.equals(userExists)){
                        return Mono.error(UserNotFoundException::new);
                    }
                    return externalPostOutputPort.verify(comment.getPost().getId())
                            .flatMap(postExists->{
                                if(Boolean.FALSE.equals(postExists)){
                                    return Mono.error(PostNotFoundException::new);
                                }
                                return commentPersistencePort.save(comment);
                            });
                });
    }

    @Override
    public Mono<Comment> update(String id, Comment comment) {
        return commentPersistencePort.findById(id)
                .switchIfEmpty(Mono.error(CommentNotFoundException::new))
                .flatMap(commentUpdated->{
                    commentUpdated.setContent(comment.getContent());
                    return commentPersistencePort.save(commentUpdated);
                });
    }

    @Override
    public Mono<Void> delete(String id) {
        return commentPersistencePort.findById(id)
                .switchIfEmpty(Mono.error(CommentNotFoundException::new))
                .flatMap(commentDeleted -> {
                    return commentPersistencePort.delete(id);
                });
    }

    @Override
    public Flux<Comment> findAllByPost(String postId) {
        return commentPersistencePort.findAllByPost(postId)
                .flatMap(comments->{
                    return externalUserOutputPort.findById(comments.getUser().getId())
                            .flatMap(user->{
                                comments.setUser(user);
                                return Mono.just(comments);
                            });
                });
    }

    @Override
    public Mono<Boolean> verifyById(String id) {
        return commentPersistencePort.verifyById(id);
    }
}
