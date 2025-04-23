package com.fernando.ms.comments.app.infrastructure.adapter.input.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fernando.ms.comments.app.application.ports.input.CommentInputPort;
import com.fernando.ms.comments.app.domain.exception.CommentNotFoundException;
import com.fernando.ms.comments.app.domain.exception.PostNotFoundException;
import com.fernando.ms.comments.app.domain.exception.UserNotFoundException;
import com.fernando.ms.comments.app.domain.models.Comment;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.CommentRestAdapter;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.mapper.CommentRestMapper;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.request.CreateCommentRequest;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.response.CommentResponse;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.response.ErrorResponse;
import com.fernando.ms.comments.app.utils.TestUtilsComment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static com.fernando.ms.comments.app.infraestructure.utils.ErrorCatalog.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = {CommentRestAdapter.class})
public class GlobalControllerAdviceTest {
    @MockitoBean
    private CommentRestMapper commentRestMapper;

    @MockitoBean
    private CommentInputPort commentInputPort;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("Expect CommentNotFoundException When Comment Identifier Is Invalid")
    void Expect_CommentNotFoundException_When_CommentIdentifierIsInvalid() {
        when(commentInputPort.findById(anyString())).thenReturn(Mono.error(new CommentNotFoundException()));

        webTestClient.get()
                .uri("/v1/comments/{id}","1")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorResponse.class)
                .value(response -> {
                    assert response.getCode().equals(COMMENT_NOT_FOUND.getCode());
                    assert response.getMessage().equals(COMMENT_NOT_FOUND.getMessage());
                });
    }

    @Test
    @DisplayName("Expect InternalServerError When Exception Occurs")
    void Expect_InternalServerError_When_ExceptionOccurs() {
        when(commentInputPort.findById(anyString())).thenReturn(Mono.error(new RuntimeException("Unexpected error")));

        webTestClient.get()
                .uri("/v1/comments/{id}","1")
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody(ErrorResponse.class)
                .value(response -> {
                    assert response.getCode().equals(COMMENT_INTERNAL_SERVER_ERROR.getCode());
                    assert response.getMessage().equals(COMMENT_INTERNAL_SERVER_ERROR.getMessage());
                });
    }
    @Test
    @DisplayName("Expect WebExchangeBindException When Comment Information Is Invalid")
    void Expect_WebExchangeBindException_When_CommentInformationIsInvalid() throws JsonProcessingException {
        CreateCommentRequest createCommentRequest= CreateCommentRequest.builder()
                .content("")
                .postId("1")
                .build();

        webTestClient.post()
                .uri("/v1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-User-Id", "1")
                .bodyValue(objectMapper.writeValueAsString(createCommentRequest))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorResponse.class)
                .value(response -> {
                    assert response.getCode().equals(COMMENT_BAD_PARAMETERS.getCode());
                    assert response.getMessage().equals(COMMENT_BAD_PARAMETERS.getMessage());
                });
    }

    @Test
    @DisplayName("Expect UserNotFoundException When Comment User Identifier Is Invalid")
    void Expect_UserNotFoundException_When_CommentUserIdentifierIsInvalid() throws JsonProcessingException {
        CreateCommentRequest createCommentRequest= TestUtilsComment.buildCreateCommentRequestMock();
        Comment comment=TestUtilsComment.buildCommentMock();
        CommentResponse commentResponse=TestUtilsComment.buildCommentResponseMock();
        when(commentRestMapper.toComment(anyLong(),any(CreateCommentRequest.class))).thenReturn(comment);
        when(commentRestMapper.toCommentResponse(any(Comment.class))).thenReturn(commentResponse);
        when(commentInputPort.save(any(Comment.class))).thenReturn(Mono.error(new UserNotFoundException()));

        webTestClient.post()
                .uri("/v1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-User-Id", "1")
                .bodyValue(objectMapper.writeValueAsString(createCommentRequest))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorResponse.class)
                .value(response -> {
                    assert response.getCode().equals(USER_NOT_FOUND.getCode());
                    assert response.getMessage().equals(USER_NOT_FOUND.getMessage());
                });
    }

    @Test
    @DisplayName("Expect PostNotFoundException When Comment Post Identifier Is Invalid")
    void Expect_PostNotFoundException_When_CommentPostIdentifierIsInvalid() throws JsonProcessingException {
        CreateCommentRequest createCommentRequest= TestUtilsComment.buildCreateCommentRequestMock();
        Comment comment=TestUtilsComment.buildCommentMock();
        CommentResponse commentResponse=TestUtilsComment.buildCommentResponseMock();
        when(commentRestMapper.toComment(anyLong(),any(CreateCommentRequest.class))).thenReturn(comment);
        when(commentRestMapper.toCommentResponse(any(Comment.class))).thenReturn(commentResponse);
        when(commentInputPort.save(any(Comment.class))).thenReturn(Mono.error(new PostNotFoundException()));
        webTestClient.post()
                .uri("/v1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-User-Id", "1")
                .bodyValue(objectMapper.writeValueAsString(createCommentRequest))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorResponse.class)
                .value(response -> {
                    assert response.getCode().equals(POST_NOT_FOUND.getCode());
                    assert response.getMessage().equals(POST_NOT_FOUND.getMessage());
                });
    }

}
