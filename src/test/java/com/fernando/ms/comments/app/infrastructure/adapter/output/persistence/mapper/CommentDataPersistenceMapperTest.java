package com.fernando.ms.comments.app.infrastructure.adapter.output.persistence.mapper;

import com.fernando.ms.comments.app.domain.models.CommentData;
import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.mapper.CommentDataPersistenceMapper;
import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.model.CommentDataDocument;
import com.fernando.ms.comments.app.utils.TestUtilCommentData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommentDataPersistenceMapperTest {

    private CommentDataPersistenceMapper commentDataPersistenceMapper;

    @BeforeEach
    void setUp(){
        commentDataPersistenceMapper= Mappers.getMapper(CommentDataPersistenceMapper.class);
    }

    @Test
    @DisplayName("When Mapping MonoCommentDataDocument Expect MonoCommentData")
    void When_MappingMonoCommentDataDocument_Expect_MonoCommentData(){
        CommentDataDocument commentDataDocument= TestUtilCommentData.buildCommentDataDocumentMock();
        Mono<CommentData> monoCommentData=commentDataPersistenceMapper.toCommentData(Mono.just(commentDataDocument));
        StepVerifier.create(monoCommentData)
                .consumeNextWith(commentData -> {
                    assertEquals(commentData.getCommentId(),commentDataDocument.getCommentId());
                    assertEquals(commentData.getId(),commentDataDocument.getId());
                    assertEquals(commentData.getUserId(),commentDataDocument.getUserId());
                    assertEquals(commentData.getTypeTarget(),commentDataDocument.getTypeTarget());
                })
                .verifyComplete();
    }
    @Test
    @DisplayName("When Mapping CommentDataDocument Expect CommentData")
    void When_MappingCommentDataDocument_Expect_CommentData(){
        CommentDataDocument commentDataDocument= TestUtilCommentData.buildCommentDataDocumentMock();
        CommentData commentData=commentDataPersistenceMapper.toCommentData(commentDataDocument);
        assertEquals(commentData.getCommentId(),commentDataDocument.getCommentId());
        assertEquals(commentData.getId(),commentDataDocument.getId());
        assertEquals(commentData.getUserId(),commentDataDocument.getUserId());
        assertEquals(commentData.getTypeTarget(),commentDataDocument.getTypeTarget());
    }

    @Test
    @DisplayName("When Mapping CommentData Expect CommentDataDocument ")
    void When_MappingCommentData_Expect_CommentDataDocument(){
        CommentData commentData= TestUtilCommentData.buildCommentDataMock();
        CommentDataDocument commentDataDocument=commentDataPersistenceMapper.toCommentDataDocument(commentData);
        assertEquals(commentDataDocument.getCommentId(),commentData.getCommentId());
        assertEquals(commentDataDocument.getId(),commentData.getId());
        assertEquals(commentDataDocument.getUserId(),commentData.getUserId());
        assertEquals(commentDataDocument.getTypeTarget(),commentData.getTypeTarget());
    }
}
