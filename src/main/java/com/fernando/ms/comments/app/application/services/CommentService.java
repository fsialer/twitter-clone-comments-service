package com.fernando.ms.comments.app.application.services;

import com.fernando.ms.comments.app.application.ports.input.CommentInputPort;
import com.fernando.ms.comments.app.application.ports.output.CommentPersistencePort;
import com.fernando.ms.comments.app.application.ports.output.ExternalPostOutputPort;
import com.fernando.ms.comments.app.application.ports.output.ExternalUserOutputPort;
import com.fernando.ms.comments.app.domain.exception.CommentNotFoundException;
import com.fernando.ms.comments.app.domain.exception.PostNotFoundException;
import com.fernando.ms.comments.app.domain.models.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CommentService implements CommentInputPort {
    private final CommentPersistencePort commentPersistencePort;
    private final ExternalPostOutputPort externalPostOutputPort;
    private final ExternalUserOutputPort externalUserOutputPort;

    @Override
    public Flux<Comment> findAll() {
        return commentPersistencePort.findAll();
    }

    @Override
    public Mono<Comment> findById(String id) {
        return commentPersistencePort.findById(id)
                .switchIfEmpty(Mono.error(CommentNotFoundException::new))
                .flatMap(comment->
                        externalUserOutputPort.findAuthorByUserId(comment.getUserId())
                                .doOnNext(comment::setAuthor)
                                .thenReturn(comment)
                );
    }

    @Override
    public Mono<Comment> save(Comment comment) {

        return externalPostOutputPort.verify(comment.getPostId())
                .filter(Boolean.TRUE::equals)
                .switchIfEmpty(Mono.error(new PostNotFoundException()))
                .flatMap(postExists->commentPersistencePort.save(comment))
                .flatMap(commentSaved->{
                    if(comment.getParentComment()!=null){
                        return commentPersistencePort.findById(comment.getParentComment())
                                .switchIfEmpty(Mono.error(CommentNotFoundException::new))
                                .flatMap(commentParent->{
                                    commentParent.getAnswers().add(commentSaved.getId());
                                    return commentPersistencePort.save(commentParent);
                                });
                    }
                    return Mono.just(commentSaved);
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
                .flatMap(commentDeleted -> commentPersistencePort.delete(id));
    }

    @Override
    public Flux<Comment> findAllByPostId(String postId, int page, int size) {
        return commentPersistencePort.findAllByPostId(postId,page,size)
                .flatMap(comment->
                    externalUserOutputPort.findAuthorByUserId(comment.getUserId())
                            .doOnNext(comment::setAuthor)
                            .thenReturn(comment)
                );
    }

    @Override
    public Mono<Boolean> verifyById(String id) {
        return commentPersistencePort.verifyById(id);
    }

    @Override
    public Mono<Long> countCommentByPostId(String postId) {
        return commentPersistencePort.countCommentByPostId(postId);
    }

    @Override
    public Mono<Boolean> verifyCommentByUserId(String commentId, String userId) {
        return commentPersistencePort.verifyCommentByUserId(commentId,userId);
    }
}
