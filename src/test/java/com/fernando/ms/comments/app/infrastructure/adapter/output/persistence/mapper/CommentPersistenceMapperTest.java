package com.fernando.ms.comments.app.infrastructure.adapter.output.persistence.mapper;

import com.fernando.ms.comments.app.domain.models.Comment;
import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.mapper.CommentPersistenceMapper;
import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.models.CommentDocument;
import com.fernando.ms.comments.app.utils.TestUtilsComment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommentPersistenceMapperTest {

    private CommentPersistenceMapper commentPersistenceMapper;

    @BeforeEach
    void setUp(){
        commentPersistenceMapper= Mappers.getMapper(CommentPersistenceMapper.class);
    }

    @Test
    @DisplayName("When MappingFluxCommentDocument Expect FluxComment")
    void When_MappingFluxCommentDocument_Expect_FluxComment(){
        CommentDocument commentDocument= TestUtilsComment.buildCommentDocumentMock();
        Flux<Comment> fluxComment=commentPersistenceMapper.toComments(Flux.just(commentDocument));

        StepVerifier.create(fluxComment)
                .consumeNextWith(comment->{
                    assertEquals(comment.getId(),commentDocument.getId());
                    assertEquals(comment.getContent(),commentDocument.getContent());
                    assertEquals(comment.getDateComment(),commentDocument.getDateComment());
                    assertEquals(comment.getPostId(),commentDocument.getPostId());
                    assertEquals(comment.getUserId(),commentDocument.getUserId());
                }).verifyComplete();
    }

    @Test
    @DisplayName("When MappingMonoCommentDocument Expect MonoComment")
    void When_MappingMonoCommentDocument_Expect_MonoComment(){
        CommentDocument commentDocument= TestUtilsComment.buildCommentDocumentMock();
        Mono<Comment> monoComment=commentPersistenceMapper.toComment(Mono.just(commentDocument));

        StepVerifier.create(monoComment)
                .consumeNextWith(comment->{
                    assertEquals(comment.getId(),commentDocument.getId());
                    assertEquals(comment.getContent(),commentDocument.getContent());
                    assertEquals(comment.getDateComment(),commentDocument.getDateComment());
                    assertEquals(comment.getPostId(),commentDocument.getPostId());
                    assertEquals(comment.getUserId(),commentDocument.getUserId());
                }).verifyComplete();

    }

    @Test
    @DisplayName("When MappingCommentDocument Expect Comment")
    void When_MappingCommentDocument_Expect_Comment(){
        CommentDocument commentDocument= TestUtilsComment.buildCommentDocumentMock();
        Comment comment=commentPersistenceMapper.toComment(commentDocument);

        assertEquals(comment.getId(),commentDocument.getId());
        assertEquals(comment.getContent(),commentDocument.getContent());
        assertEquals(comment.getDateComment(),commentDocument.getDateComment());
        assertEquals(comment.getPostId(),commentDocument.getPostId());
        assertEquals(comment.getUserId(),commentDocument.getUserId());
    }

    @Test
    @DisplayName("When MappingComment Expect CommentDocument")
    void When_MappingComment_Expect_CommentDocument(){
        Comment comment= TestUtilsComment.buildCommentMock();
        CommentDocument commentDocument=commentPersistenceMapper.toCommentDocument(comment);

        assertEquals(commentDocument.getId(),comment.getId());
        assertEquals(commentDocument.getContent(),comment.getContent());
        assertEquals(commentDocument.getPostId(),comment.getPostId());
        assertEquals(commentDocument.getUserId(),comment.getUserId());
    }
}
