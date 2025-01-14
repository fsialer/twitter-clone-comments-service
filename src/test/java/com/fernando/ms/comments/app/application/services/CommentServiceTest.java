package com.fernando.ms.comments.app.application.services;

import com.fernando.ms.comments.app.application.ports.output.CommentPersistencePort;
import com.fernando.ms.comments.app.domain.exception.CommentNotFoundException;
import com.fernando.ms.comments.app.domain.models.Comment;
import com.fernando.ms.comments.app.utils.TestUtilsComment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    @Mock
    private CommentPersistencePort commentPersistencePort;

    @InjectMocks
    private CommentService commentService;
    @Test
    @DisplayName("When Comments Information Is Correct Expect A List Comments")
    void When_CommentsInformationIsCorrect_Expect_AListComments(){
        Comment comment= TestUtilsComment.buildCommentMock();
        when(commentPersistencePort.findAll()).thenReturn(Flux.just(comment));

        Flux<Comment> comments=commentService.findAll();
        StepVerifier.create(comments)
                .expectNext(comment)
                .verifyComplete();
        Mockito.verify(commentPersistencePort,times(1)).findAll();
    }

    @Test
    @DisplayName("When Comment Identifier Is Correct Except Comment Information Correct")
    void When_CommentIdentifierIsCorrect_Except_CommentInformationCorrect(){
        Comment comment= TestUtilsComment.buildCommentMock();
        when(commentPersistencePort.findById(anyString())).thenReturn(Mono.just(comment));
        Mono<Comment> comentMono=commentService.findById("1");
        StepVerifier.create(comentMono)
                .expectNext(comment)
                .verifyComplete();
        Mockito.verify(commentPersistencePort,times(1)).findById(anyString());
    }

    @Test
    @DisplayName("Expect CommentNotFoundException When Comment Identifier Is Invalid")
    void Expect_CommentNotFoundException_When_CommentIdentifierIsInvalid(){
        when(commentPersistencePort.findById(anyString())).thenReturn(Mono.empty());
        Mono<Comment> userMono=commentService.findById("1");
        StepVerifier.create(userMono)
                .expectError(CommentNotFoundException.class)
                .verify();
        Mockito.verify(commentPersistencePort,times(1)).findById(anyString());
    }


}
