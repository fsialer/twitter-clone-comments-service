package com.fernando.ms.comments.app.application.services;

import com.fernando.ms.comments.app.application.ports.output.CommentDataPersistencePort;
import com.fernando.ms.comments.app.application.ports.output.CommentPersistencePort;
import com.fernando.ms.comments.app.domain.exception.CommentNotFoundException;
import com.fernando.ms.comments.app.domain.exception.CommentRuleException;
import com.fernando.ms.comments.app.domain.models.Comment;
import com.fernando.ms.comments.app.domain.models.CommentData;
import com.fernando.ms.comments.app.domain.models.Post;
import com.fernando.ms.comments.app.utils.TestUtilCommentData;
import com.fernando.ms.comments.app.utils.TestUtilsComment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
public class CommentDataServiceTest {
    @Mock
    private CommentDataPersistencePort commentDataPersistencePort;

    @Mock
    private CommentPersistencePort commentPersistencePort;

    @InjectMocks
    private CommentDataService commentDataService;

    @Test
    @DisplayName("When CommentData Information Is Correct Expect Save Information")
    void When_PostDataInformationIsCorrect_Expect_SaveInformation(){
        CommentData commentData= TestUtilCommentData.buildCommentDataMock();
        Comment comment= TestUtilsComment.buildCommentMock();

        when(commentPersistencePort.findById(anyString())).thenReturn(Mono.just(comment));
        when(commentDataPersistencePort.verifyCommentData(anyString(),anyString())).thenReturn(Mono.just(Boolean.FALSE));
        when(commentDataPersistencePort.save(commentData)).thenReturn(Mono.empty());

        Mono<Void> commentSaved=commentDataService.save(commentData);
        StepVerifier.create(commentSaved)
                .verifyComplete();
        Mockito.verify(commentDataPersistencePort,times(1)).save(any(CommentData.class));
        Mockito.verify(commentPersistencePort,times(1)).findById(anyString());
        Mockito.verify(commentDataPersistencePort,times(1)).verifyCommentData(anyString(),anyString());
    }

    @Test
    @DisplayName("Expect CommentNotFoundException When Comment Does Not Exists")
    void Expect_CommentNotFoundException_When_CommentDoesNotExists(){
        CommentData commentData= TestUtilCommentData.buildCommentDataMock();
        when(commentPersistencePort.findById(anyString())).thenReturn(Mono.empty());
        Mono<Void> posts=commentDataService.save(commentData);
        StepVerifier.create(posts)
                .expectError(CommentNotFoundException.class)
                .verify();
        Mockito.verify(commentDataPersistencePort,never()).save(any(CommentData.class));
        Mockito.verify(commentPersistencePort,times(1)).findById(anyString());
        Mockito.verify(commentDataPersistencePort,never()).verifyCommentData(anyString(),anyString());
    }

    @Test
    @DisplayName("Expect CommentRuleException When CommentData Already Exists")
    void Expect_CommentRuleException_When_CommentDataAlreadyExists(){
        CommentData commentData= TestUtilCommentData.buildCommentDataMock();
        Comment comment= TestUtilsComment.buildCommentMock();

        when(commentPersistencePort.findById(anyString())).thenReturn(Mono.just(comment));
        when(commentDataPersistencePort.verifyCommentData(anyString(),anyString())).thenReturn(Mono.just(Boolean.TRUE));

        Mono<Void> posts=commentDataService.save(commentData);
        StepVerifier.create(posts)
                .expectError(CommentRuleException.class)
                .verify();
        Mockito.verify(commentDataPersistencePort,never()).save(any(CommentData.class));
        Mockito.verify(commentPersistencePort,times(1)).findById(anyString());
        Mockito.verify(commentDataPersistencePort,times(1)).verifyCommentData(anyString(),anyString());
    }

}
