package com.fernando.ms.comments.app.infrastructure.adapter.input.rest.mapper;

import com.fernando.ms.comments.app.domain.models.Comment;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.mapper.CommentRestMapper;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.request.CreateCommentRequest;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.request.UpdateCommentRequest;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.response.*;
import com.fernando.ms.comments.app.utils.TestUtilsComment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommentRestMapperTest {

    private CommentRestMapper commentRestMapper;

    @BeforeEach
    void setUp(){
        commentRestMapper= Mappers.getMapper(CommentRestMapper.class);
    }

    @Test
    @DisplayName("When Mapping FluxComment Expect FluxCommentResponse")
    void When_MappingFluxComment_Expect_FluxCommentResponse(){
        Comment comment= TestUtilsComment.buildCommentMock();
        Flux<CommentResponse> fluxCommentResponse=commentRestMapper.toCommentsResponse(Flux.just(comment));
        StepVerifier.create(fluxCommentResponse)
                .consumeNextWith(commentResponse -> {
                    assertEquals(commentResponse.getId(),comment.getId());
                    assertEquals(commentResponse.getContent(),comment.getContent());
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("When Mapping FluxComment Expect FluxUserCommentResponse")
    void When_MappingFluxComment_Expect_FluxUserCommentResponse(){
        Comment comment= TestUtilsComment.buildCommentMock();
        Flux<CommentUserResponse> fluxCommentUserResponse=commentRestMapper.toCommentsAuthorResponse(Flux.just(comment));
        StepVerifier.create(fluxCommentUserResponse)
                .consumeNextWith(commentResponse -> {
                    assertEquals(commentResponse.getId(),comment.getId());
                    assertEquals(commentResponse.getContent(),comment.getContent());
                    assertEquals(commentResponse.getAuthor(),comment.getAuthor().getNames().concat(" ").concat(comment.getAuthor().getLastNames()));
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("When Mapping Comment Expect CommentUserResponse")
    void When_MappingComment_Expect_CommentUserResponse(){
        Comment comment= TestUtilsComment.buildCommentMock();
        CommentUserResponse commentUserResponse=commentRestMapper.toCommentAuthorResponse(comment);
        assertEquals(commentUserResponse.getId(),comment.getId());
        assertEquals(commentUserResponse.getContent(),comment.getContent());
        assertEquals(commentUserResponse.getAuthor(),comment.getAuthor().getNames().concat(" ").concat(comment.getAuthor().getLastNames()==null?"":comment.getAuthor().getLastNames()).trim());
    }

    @Test
    @DisplayName("When Mapping Comment WithOut LastNames Expect CommentUserResponse")
    void When_MappingCommentWithOutLastNames_Expect_CommentUserResponse(){
        Comment comment= TestUtilsComment.buildCommentMock();
        comment.getAuthor().setLastNames(null);
        CommentUserResponse commentUserResponse=commentRestMapper.toCommentAuthorResponse(comment);
        assertEquals(commentUserResponse.getId(),comment.getId());
        assertEquals(commentUserResponse.getContent(),comment.getContent());
        assertEquals(commentUserResponse.getAuthor(),comment.getAuthor().getNames().concat(" ").concat(comment.getAuthor().getLastNames()==null?"":comment.getAuthor().getLastNames()).trim());
    }

    @Test
    @DisplayName("When Mapping Comment Expect CommentResponse")
    void When_MappingComment_Expect_CommentResponse(){
        Comment comment= TestUtilsComment.buildCommentMock();
        CommentResponse commentResponse=commentRestMapper.toCommentResponse(comment);
        assertEquals(commentResponse.getId(),comment.getId());
        assertEquals(commentResponse.getContent(),comment.getContent());
    }

    @Test
    @DisplayName("When Mapping UserIDAndCreateCommentRequest Expect Comment")
    void When_MappingUserIDAndCreateCommentRequest_Expect_Comment(){
        CreateCommentRequest createCommentRequest= TestUtilsComment.buildCreateCommentRequestMock();
        String userId="48gf45f6df8df";
        Comment comment=commentRestMapper.toComment(userId,createCommentRequest);
        assertEquals(comment.getPostId(),createCommentRequest.getPostId());
        assertEquals(comment.getContent(),createCommentRequest.getContent());
        assertEquals(userId, comment.getUserId());
    }

    @Test
    @DisplayName("When Mapping UpdateCommentRequest Expect Comment")
    void When_MappingUpdateCommentRequest_Expect_Comment(){
        UpdateCommentRequest updateCommentRequest= TestUtilsComment.buildUpdateCommentRequestMock();
        Comment comment=commentRestMapper.toComment(updateCommentRequest);
        assertEquals(comment.getContent(),updateCommentRequest.getContent());
    }

    @Test
    @DisplayName("When Mapping Boolean Expect ExistsCommentResponse")
    void When_MappingBoolean_Expect_ExistsCommentResponse(){
        ExistsCommentResponse existsCommentResponse=commentRestMapper.toExistsCommentResponse(Boolean.TRUE);
        assertTrue(existsCommentResponse.getExists());
    }

    @Test
    @DisplayName("When Mapping Long Expect MonoCountCommentResponse")
    void When_MappingLong_Expect_MonoCountCommentResponse(){
        Mono<CountCommentResponse> countCommentResponse=commentRestMapper.toCountCommentResponse(2L);
        StepVerifier.create(countCommentResponse)
                .consumeNextWith(countComment->{
                    assertEquals(2L, countComment.quantity());
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("When Mapping Boolean Expect ExistsCommentUserResponse")
    void When_MappingBoolean_Expect_ExistsCommentUserResponse(){
        ExistsCommentUserResponse existsCommentUserResponse=commentRestMapper.toExistsCommentUserResponse(Boolean.TRUE);
        assertTrue(existsCommentUserResponse.exists());
    }
}
