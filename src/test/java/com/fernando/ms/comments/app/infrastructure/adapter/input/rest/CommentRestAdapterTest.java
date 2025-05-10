package com.fernando.ms.comments.app.infrastructure.adapter.input.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fernando.ms.comments.app.application.ports.input.CommentDataInputPort;
import com.fernando.ms.comments.app.application.ports.input.CommentInputPort;
import com.fernando.ms.comments.app.domain.models.Comment;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.CommentRestAdapter;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.mapper.CommentDataRestMapper;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.mapper.CommentRestMapper;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.request.CreateCommentDataRequest;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.request.CreateCommentRequest;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.request.UpdateCommentRequest;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.response.CommentResponse;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.response.CommentUserResponse;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.response.ExistsCommentResponse;
import com.fernando.ms.comments.app.utils.TestUtilCommentData;
import com.fernando.ms.comments.app.utils.TestUtilsComment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@WebFluxTest(CommentRestAdapter.class)
class CommentRestAdapterTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private CommentInputPort commentInputPort;

    @MockitoBean
    private CommentRestMapper commentRestMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CommentDataInputPort commentDataInputPort;

    @MockitoBean
    private CommentDataRestMapper commentDataRestMapper;

    @Test
    @DisplayName("When Comments Are Exists Expect Comments Information Return Successfully")
    void When_CommentsAreExists_Expect_CommentsInformationReturnSuccessfully() {
        CommentResponse commentResponse = TestUtilsComment.buildCommentResponseMock();
        Comment comment=TestUtilsComment.buildCommentMock();

        when(commentInputPort.findAll()).thenReturn(Flux.just(comment));
        when(commentRestMapper.toCommentsResponse(any(Flux.class))).thenReturn(Flux.just(commentResponse));

        webTestClient.get()
                .uri("/v1/comments")
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
                .uri("/v1/comments/{id}",1L)
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

        when(commentRestMapper.toComment(anyString(),any(CreateCommentRequest.class))).thenReturn(comment);
        when(commentInputPort.save(any(Comment.class))).thenReturn(Mono.just(comment));
        when(commentRestMapper.toCommentResponse(any(Comment.class))).thenReturn(commentResponse);

        webTestClient.post()
                .uri("/v1/comments")
                .header("X-User-Id","1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(objectMapper.writeValueAsString(createPostRequest))
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.content").isEqualTo("comment");

        Mockito.verify(commentRestMapper, times(1)).toComment(anyString(),any(CreateCommentRequest.class));
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
                .uri("/v1/comments/{id}","1")
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
                .uri("/v1/comments/{id}", "1")
                .exchange()
                .expectStatus().isNoContent();

        Mockito.verify(commentInputPort, times(1)).delete(anyString());
    }

    @Test
    @DisplayName("When Post Identifier Is Correct Expect A List Of Comments")
    void When_PostIdentifierIsCorrect_Expect_AListOfComments() {
        // Mock de datos
        Comment comment = TestUtilsComment.buildCommentMock();
        CommentUserResponse commentUserResponse = TestUtilsComment.buildCommentUserResponseMock();

        // Configuración de mocks
        when(commentInputPort.findAllByPostId(anyString())).thenReturn(Flux.just(comment));
        when(commentRestMapper.toCommentsUserResponse(any(Flux.class))).thenReturn(Flux.just(commentUserResponse));

        // Ejecución y validación
        webTestClient.get()
                .uri("/v1/comments/{idPost}/post", "1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].content").isEqualTo(commentUserResponse.getContent());

        // Verificación de interacciones
        Mockito.verify(commentInputPort, times(1)).findAllByPostId(anyString());
        Mockito.verify(commentRestMapper, times(1)).toCommentsUserResponse(any(Flux.class));
    }

    @Test
    @DisplayName("When Comment Verification Is Incorrect Expect Comment Do Not Verified")
    void When_CommentVerificationIsIncorrect_Expect_CommentDoNotVerified() {
        ExistsCommentResponse existsCommentResponse = TestUtilsComment.buildExistsCommentResponseMock();
        existsCommentResponse.setExists(false);
        when(commentInputPort.verifyById(anyString())).thenReturn(Mono.just(false));
        when(commentRestMapper.toExistsCommentResponse(anyBoolean())).thenReturn(existsCommentResponse);

        webTestClient.get()
                .uri("/v1/comments/{id}/verify", 1L)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.exists").isEqualTo(false);

        Mockito.verify(commentInputPort, times(1)).verifyById(anyString());
        Mockito.verify(commentRestMapper, times(1)).toExistsCommentResponse(anyBoolean());
    }

    @Test
    @DisplayName("When Post Identifier Is Correct Expect A List Of Comments With User Information")
    void When_PostIdentifierIsCorrect_Expect_AListOfCommentsWithUserInformation() {
        CommentUserResponse commentUserResponse = TestUtilsComment.buildCommentUserResponseMock();
        Comment comment = TestUtilsComment.buildCommentMock();

        when(commentInputPort.findAllByPostId(anyString())).thenReturn(Flux.just(comment));
        when(commentRestMapper.toCommentsUserResponse(any(Flux.class))).thenReturn(Flux.just(commentUserResponse));

        webTestClient.get()
                .uri("/v1/comments/{idPost}/post", "1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isNotEmpty();

        Mockito.verify(commentInputPort, times(1)).findAllByPostId(anyString());
        Mockito.verify(commentRestMapper, times(1)).toCommentsUserResponse(any(Flux.class));
    }

    @Test
    @DisplayName("when CommentData Is Valid Expect Save Data Successfully")
    void when_CommentDataIsValid_Expect_SaveDataSuccessfully() {
        CreateCommentDataRequest createPostDataRequest = TestUtilCommentData.buildCreateCommentDataRequestMock();
        when(commentDataRestMapper.toCommentData(any(String.class), any(CreateCommentDataRequest.class)))
                .thenReturn(TestUtilCommentData.buildCommentDataMock());
        when(commentDataInputPort.save(any())).thenReturn(Mono.empty());
        webTestClient.post()
                .uri("/v1/comments/data")
                .header("X-User-Id", "1")
                .bodyValue(createPostDataRequest)
                .exchange()
                .expectStatus().isNoContent();
        Mockito.verify(commentDataRestMapper, times(1))
                .toCommentData(any(String.class), any(CreateCommentDataRequest.class));
        Mockito.verify(commentDataInputPort, times(1)).save(any());
    }

    @Test
    @DisplayName("When Delete CommentData By Id Expect Complete Successfully")
    void When_DeleteCommentDataById_Expect_CompleteSuccessfully() {
        String postDataId = "postDataId123";
        when(commentDataInputPort.delete(anyString())).thenReturn(Mono.empty());
        webTestClient.delete()
                .uri("/v1/comments/data/{id}", postDataId)
                .header("X-User-Id", "1")
                .exchange()
                .expectStatus().isNoContent();

        Mockito.verify(commentDataInputPort, times(1)).delete(postDataId);
    }





}
