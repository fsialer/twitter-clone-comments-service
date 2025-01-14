package com.fernando.ms.comments.app.infraestructure.adapter.output.persistence;

import com.fernando.ms.comments.app.application.ports.output.CommentPersistencePort;
import com.fernando.ms.comments.app.domain.models.Comment;
import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.Models.CommentDocument;
import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.Models.CommentPost;
import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.Models.CommentUser;
import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.mapper.CommentPersistenceMapper;
import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.repository.CommentReactiveMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

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

    @Override
    public Mono<Comment> save(Comment comment) {
        CommentDocument commentDocument=commentPersistenceMapper.toCommentDocument(comment);
        if(comment.getId()==null){
            CommentUser commentUser = CommentUser.builder()
                    .userId(comment.getUser().getId())
                    .build();
            CommentPost commentPost = CommentPost.builder()
                    .postId(comment.getPost().getId())
                    .build();
            commentDocument.setCommentUser(commentUser);
            commentDocument.setCommentPost(commentPost);
        }else{
            return commentReactiveMongoRepository.findById(comment.getId())
                    .flatMap(commentDocument1 -> {
                        commentDocument.setCommentUser(commentDocument1.getCommentUser());
                        commentDocument.setCommentPost(commentDocument1.getCommentPost());
                        commentDocument.setDateComment(commentDocument1.getDateComment());
                        commentDocument.setCreatedAt(commentDocument1.getCreatedAt());
                        commentDocument.setUpdatedAt(LocalDateTime.now());
                        return commentPersistenceMapper.toComment(commentReactiveMongoRepository.save(commentDocument));
                    });
        }
        return commentPersistenceMapper.toComment(commentReactiveMongoRepository.save(commentDocument));
    }

    @Override
    public Mono<Void> delete(String id) {
        return commentReactiveMongoRepository.deleteById(id);
    }

    @Override
    public Flux<Comment> findAllByPost(String postId) {
        CommentPost commentPost = CommentPost.builder()
                .postId(postId)
                .build();
        return commentPersistenceMapper.toComments(commentReactiveMongoRepository.findAllByCommentPost(commentPost));
    }
}
