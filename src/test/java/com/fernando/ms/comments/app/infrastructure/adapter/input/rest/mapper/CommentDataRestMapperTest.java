package com.fernando.ms.comments.app.infrastructure.adapter.input.rest.mapper;

import com.fernando.ms.comments.app.domain.models.CommentData;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.mapper.CommentDataRestMapper;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.request.CreateCommentDataRequest;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.response.CountCommentDataResponse;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.response.ExistsCommentDataResponse;
import com.fernando.ms.comments.app.utils.TestUtilCommentData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommentDataRestMapperTest {

    private CommentDataRestMapper commentDataRestMapper;

    @BeforeEach
    void setUp(){
        commentDataRestMapper= Mappers.getMapper(CommentDataRestMapper.class);
    }

    @Test
    @DisplayName("When Mapping CreateCommentDataRequest Expect CommentData")
    void When_MappingCreateCommentDataRequest_Expect_CommentData(){
        CreateCommentDataRequest createCommentDataRequest= TestUtilCommentData.buildCreateCommentDataRequestMock();
        CommentData commentData=commentDataRestMapper.toCommentData(createCommentDataRequest);
        assertEquals(commentData.getCommentId(),createCommentDataRequest.getCommentId());
        assertEquals(commentData.getTypeTarget(),createCommentDataRequest.getTypeTarget());
    }

    @Test
    @DisplayName("When Mapping UserId And CreateCommentDataRequest Expect CommentData")
    void When_MappingUserIdAndCreateCommentDataRequest_Expect_CommentData(){
        CreateCommentDataRequest createCommentDataRequest= TestUtilCommentData.buildCreateCommentDataRequestMock();
        String userId="dsd154gg154d5sd";
        CommentData commentData=commentDataRestMapper.toCommentData(userId,createCommentDataRequest);
        assertEquals(commentData.getCommentId(),createCommentDataRequest.getCommentId());
        assertEquals(commentData.getTypeTarget(),createCommentDataRequest.getTypeTarget());
    }

    @Test
    @DisplayName("When Mapping Long Expect MonoCountCommentDataResponse")
    void When_MappingLong_Expect_MonoCountCommentDataResponse(){
        Mono<CountCommentDataResponse> countCommentResponse=commentDataRestMapper.toCountCommentDataResponse(2L);
        StepVerifier.create(countCommentResponse)
                .consumeNextWith(countComment->{
                    assertEquals(2L, countComment.quantity());
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("When Mapping Boolean Expect MonoExistsCommentDataResponse")
    void When_MappingBoolean_Expect_MonoExistsCommentDataResponse(){
        Mono<ExistsCommentDataResponse> existsPostDataResponseMono=commentDataRestMapper.toExistsCommentDataResponse(Boolean.TRUE);
        StepVerifier.create(existsPostDataResponseMono)
                .consumeNextWith(countCommentData->{
                    assertEquals(true, countCommentData.exists());
                })
                .verifyComplete();
    }
}
