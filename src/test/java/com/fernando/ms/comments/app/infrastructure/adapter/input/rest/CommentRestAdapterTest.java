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
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.response.*;
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
        CommentUserResponse commentAuthorResponse = TestUtilsComment.buildCommentUserResponseMock();
        Comment comment=TestUtilsComment.buildCommentMock();
        when(commentInputPort.findById(anyString())).thenReturn(Mono.just(comment));
        when(commentRestMapper.toCommentAuthorResponse(any(Comment.class))).thenReturn(commentAuthorResponse);
        webTestClient.get()
                .uri("/v1/comments/{id}",1L)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.content").isEqualTo("comment");

        Mockito.verify(commentInputPort,times(1)).findById(anyString());
        Mockito.verify(commentRestMapper,times(1)).toCommentAuthorResponse(any(Comment.class));
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
        when(commentInputPort.findAllByPostId(anyString(),anyInt(),anyInt())).thenReturn(Flux.just(comment));
        when(commentRestMapper.toCommentsAuthorResponse(any(Flux.class))).thenReturn(Flux.just(commentUserResponse));

        // Ejecución y validación
        webTestClient.get()
                .uri( uriBuilder ->
                    uriBuilder.path("/v1/comments/{idPost}/post")
                            .queryParam("page",1)
                            .queryParam("size",20)
                            .build("1")
                )
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].content").isEqualTo(commentUserResponse.getContent());

        // Verificación de interacciones
        Mockito.verify(commentInputPort, times(1)).findAllByPostId(anyString(),anyInt(),anyInt());
        Mockito.verify(commentRestMapper, times(1)).toCommentsAuthorResponse(any(Flux.class));
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
        String commentId = "commentId";
        when(commentDataInputPort.delete(anyString(),anyString())).thenReturn(Mono.empty());
        webTestClient.delete()
                .uri("/v1/comments/data/{commentId}", commentId)
                .header("X-User-Id", "1")
                .exchange()
                .expectStatus().isNoContent();

        Mockito.verify(commentDataInputPort, times(1)).delete(anyString(),anyString());
    }

    @Test
    @DisplayName("When PostId Is Valid Expect Quantity Comments By PostId")
    void When_PostIdIsValid_Expect_QuantityCommentByPostId() {
        CountCommentResponse countCommentResponse = TestUtilsComment.buiildCountCommentResponseMock();

        when(commentInputPort.countCommentByPostId(anyString())).thenReturn(Mono.just(2L));
        when(commentRestMapper.toCountCommentResponse(anyLong())).thenReturn(Mono.just(countCommentResponse));

        webTestClient.get()
                .uri( uriBuilder -> uriBuilder
                        .path("/v1/comments/{postId}/count")
                        .build("1")
                )
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.quantity").isEqualTo(countCommentResponse.quantity());

        Mockito.verify(commentInputPort, times(1)).countCommentByPostId(anyString());
        Mockito.verify(commentRestMapper, times(1)).toCountCommentResponse(anyLong());
    }

    @Test
    @DisplayName("When CommentId Is Valid Expect Quantity Comments Data By CommentId")
    void When_CommentIdIsValid_Expect_QuantityCommentsDataByCommentId() {
        CountCommentDataResponse countCommentDataResponse = TestUtilsComment.buiildCountCommentDataResponseMock();

        when(commentDataInputPort.countCommentDataByComment(anyString())).thenReturn(Mono.just(2L));
        when(commentDataRestMapper.toCountCommentDataResponse(anyLong())).thenReturn(Mono.just(countCommentDataResponse));

        webTestClient.get()
                .uri( uriBuilder -> uriBuilder
                        .path("/v1/comments/data/{commentId}/count")
                        .build("1")
                )
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.quantity").isEqualTo(countCommentDataResponse.quantity());

        Mockito.verify(commentDataInputPort, times(1)).countCommentDataByComment(anyString());
        Mockito.verify(commentDataRestMapper, times(1)).toCountCommentDataResponse(anyLong());
    }

    @Test
    @DisplayName("When CommentId And UserId Is Valid Expect True")
    void When_PostIdAndUserIdIsValid_Expect_True() {
        ExistsCommentDataResponse existsCommentDataResponse = TestUtilCommentData.buildExistsCommentDataResponseMock();

        when(commentDataInputPort.verifyExistsCommentData(anyString(),anyString())).thenReturn(Mono.just(Boolean.TRUE));
        when(commentDataRestMapper.toExistsCommentDataResponse(anyBoolean())).thenReturn(Mono.just(existsCommentDataResponse));

        webTestClient.get()
                .uri( uriBuilder -> uriBuilder
                        .path("/v1/comments/data/{commentId}/exists")
                        .build("1")
                )
                .header("X-User-Id","1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.exists").isEqualTo(existsCommentDataResponse.exists());

        Mockito.verify(commentDataInputPort, times(1)).verifyExistsCommentData(anyString(),anyString());
        Mockito.verify(commentDataRestMapper, times(1)).toExistsCommentDataResponse(anyBoolean());
    }

    @Test
    @DisplayName("When CommentId And UserId Is Valid Expect True")
    void When_CommentIdAndUserIdAreValid_Expect_True() {
        ExistsCommentUserResponse existsCommentUserResponse = TestUtilsComment.buildExistsCommentUserResponseMock();

        when(commentInputPort.verifyCommentByUserId(anyString(),anyString())).thenReturn(Mono.just(Boolean.TRUE));
        when(commentRestMapper.toExistsCommentUserResponse(anyBoolean())).thenReturn(existsCommentUserResponse);

        webTestClient.get()
                .uri( uriBuilder -> uriBuilder
                        .path("/v1/comments/{commentId}/user")
                        .build("1")
                )
                .header("X-User-Id","1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.exists").isEqualTo(existsCommentUserResponse.exists());

        Mockito.verify(commentInputPort, times(1)).verifyCommentByUserId(anyString(),anyString());
        Mockito.verify(commentRestMapper, times(1)).toExistsCommentUserResponse(anyBoolean());
    }


}
