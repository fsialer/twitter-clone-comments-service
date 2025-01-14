package com.fernando.ms.comments.app.infrastructure.adapter.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fernando.ms.comments.app.application.ports.input.CommentInputPort;
import com.fernando.ms.comments.app.domain.models.Comment;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.CommentRestAdapter;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.mapper.CommentRestMapper;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.response.CommentResponse;
import com.fernando.ms.comments.app.utils.TestUtilsComment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.mockito.ArgumentMatchers.any;
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

}
