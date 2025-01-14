package com.fernando.ms.comments.app.infrastructure.adapter.input.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fernando.ms.comments.app.application.ports.input.CommentInputPort;
import com.fernando.ms.comments.app.domain.models.Comment;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.CommentRestAdapter;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.mapper.CommentRestMapper;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.request.CreateCommentRequest;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.request.UpdateCommentRequest;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.response.CommentResponse;
import com.fernando.ms.comments.app.utils.TestUtilsComment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@WebFluxTest(CommentRestAdapter.class)
public class CommentRestAdapterTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CommentInputPort commentInputPort;

    @MockBean
    private CommentRestMapper commentRestMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("When Comments Are Exists Expect Comments Information Return Successfully")
    void When_CommentsAreExists_Expect_CommentsInformationReturnSuccessfully() {
        CommentResponse commentResponse = TestUtilsComment.buildCommentResponseMock();
        Comment comment=TestUtilsComment.buildCommentMock();

        when(commentInputPort.findAll()).thenReturn(Flux.just(comment));
        when(commentRestMapper.toCommentsResponse(any(Flux.class))).thenReturn(Flux.just(commentResponse));

        webTestClient.get()
                .uri("/comments")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].content").isEqualTo("comment");
        Mockito.verify(commentInputPort,times(1)).findAll();
        Mockito.verify(commentRestMapper,times(1)).toCommentsResponse(any(Flux.class));
    }

    @Test
    @DisplayName("When Comment Identifier Is Correct Expect Comment Information Successfully")
    void When_CommentIdentifierIsCorrect_Expect_CommentInformationSuccessfully() {
        CommentResponse commentResponse = TestUtilsComment.buildCommentResponseMock();
        Comment comment=TestUtilsComment.buildCommentMock();
        when(commentInputPort.findById(anyString())).thenReturn(Mono.just(comment));
        when(commentRestMapper.toCommentResponse(any(Comment.class))).thenReturn(commentResponse);
        webTestClient.get()
                .uri("/comments/{id}",1L)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.content").isEqualTo("comment");

        Mockito.verify(commentInputPort,times(1)).findById(anyString());
        Mockito.verify(commentRestMapper,times(1)).toCommentResponse(any(Comment.class));
    }

    @Test
    @DisplayName("When Comment Is Saved Successfully Expect Comment Information Correct")
    void When_CommentIsSavedSuccessfully_Expect_CommentInformationCorrect() throws JsonProcessingException {
        CreateCommentRequest createPostRequest = TestUtilsComment.buildCreateCommentRequestMock();
        Comment comment = TestUtilsComment.buildCommentMock();
        CommentResponse commentResponse = TestUtilsComment.buildCommentResponseMock();

        when(commentRestMapper.toComment(any(CreateCommentRequest.class))).thenReturn(comment);
        when(commentInputPort.save(any(Comment.class))).thenReturn(Mono.just(comment));
        when(commentRestMapper.toCommentResponse(any(Comment.class))).thenReturn(commentResponse);

        webTestClient.post()
                .uri("/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(objectMapper.writeValueAsString(createPostRequest))
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.content").isEqualTo("comment");

        Mockito.verify(commentRestMapper, times(1)).toComment(any(CreateCommentRequest.class));
        Mockito.verify(commentInputPort, times(1)).save(any(Comment.class));
        Mockito.verify(commentRestMapper, times(1)).toCommentResponse(any(Comment.class));
    }

    @Test
    @DisplayName("When Comment Information Is Correct Expect Update Comment Successfully")
    void When_CommentInformationIsCorrect_Expect_UpdateCommentSuccessfully() throws JsonProcessingException {
        UpdateCommentRequest updateCommentRequest= TestUtilsComment.buildUpdateCommentRequestMock();
        Comment comment=TestUtilsComment.buildCommentMock();
        CommentResponse commentResponse=TestUtilsComment.buildCommentResponseMock();
        when(commentRestMapper.toComment(any(UpdateCommentRequest.class))).thenReturn(comment);
        when(commentInputPort.update(anyString(),any(Comment.class))).thenReturn(Mono.just(comment));
        when(commentRestMapper.toCommentResponse(any(Comment.class))).thenReturn(commentResponse);

        webTestClient.put()
                .uri("/comments/{id}","1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(objectMapper.writeValueAsString(updateCommentRequest))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.content").isEqualTo("comment");
    }

    @Test
    @DisplayName("When Comment Exists Expect Comment Deleted Successfully")
    void When_CommentExists_Expect_CommentDeletedSuccessfully() {
        when(commentInputPort.delete(anyString())).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/comments/{id}", "1")
                .exchange()
                .expectStatus().isNoContent();

        Mockito.verify(commentInputPort, times(1)).delete(anyString());
    }

    @Test
    @DisplayName("When Post Identifier Is Correct Expect A List Of Comments")
    void When_PostIdentifierIsCorrect_Expect_AListOfComments() {
        CommentResponse commentResponse = TestUtilsComment.buildCommentResponseMock();
        Comment comment = TestUtilsComment.buildCommentMock();

        when(commentInputPort.findAllByPost(anyString())).thenReturn(Flux.just(comment));
        when(commentRestMapper.toCommentsResponse(any(Flux.class))).thenReturn(Flux.just(commentResponse));

        webTestClient.get()
                .uri("/comments/{idPost}/post", "1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].content").isEqualTo("comment");

        Mockito.verify(commentInputPort, times(1)).findAllByPost(anyString());
        Mockito.verify(commentRestMapper, times(1)).toCommentsResponse(any(Flux.class));
    }
}
