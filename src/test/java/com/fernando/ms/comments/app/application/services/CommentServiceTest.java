package com.fernando.ms.comments.app.application.services;

import com.fernando.ms.comments.app.application.ports.output.CommentPersistencePort;
import com.fernando.ms.comments.app.application.ports.output.ExternalPostOutputPort;
import com.fernando.ms.comments.app.domain.exception.CommentNotFoundException;
import com.fernando.ms.comments.app.domain.exception.PostNotFoundException;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    @Mock
    private CommentPersistencePort commentPersistencePort;

    @Mock
    private ExternalPostOutputPort externalPostOutputPort;


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

    @Test
    @DisplayName("When Comment Is Saved Successfully Expect Comment Information Correct")
    void When_CommentIsSavedSuccessfully_Expect_CommentInformationCorrect() {
        Comment comment = TestUtilsComment.buildCommentMock();
        when(commentPersistencePort.save(any(Comment.class))).thenReturn(Mono.just(comment));
        when(externalPostOutputPort.verify(anyString())).thenReturn(Mono.just(true));
        Mono<Comment> savedComment = commentService.save(comment);

        StepVerifier.create(savedComment)
                .expectNext(comment)
                .verifyComplete();
        Mockito.verify(commentPersistencePort, times(1)).save(any(Comment.class));
        Mockito.verify(externalPostOutputPort, times(1)).verify(anyString());
    }

    @Test
    @DisplayName("When Comment Is Update Except Comment Information Save Correctly")
    void When_CommentIsUpdateExcept_CommentInformationSaveCorrectly(){
        Comment comment = TestUtilsComment.buildCommentMock();
        when(commentPersistencePort.findById(anyString())).thenReturn(Mono.just(comment));
        when(commentPersistencePort.save(any(Comment.class))).thenReturn(Mono.just(comment));

        Mono<Comment> updateComment=commentService.update("1",comment);
        StepVerifier.create(updateComment)
                .expectNext(comment)
                .verifyComplete();
        Mockito.verify(commentPersistencePort,times(1)).save(any(Comment.class));
        Mockito.verify(commentPersistencePort,times(1)).findById(anyString());
    }

    @Test
    @DisplayName("Expect CommentNotFoundException When Updated Comment Identifier Is Invalid")
    void Expect_CommentNotFoundException_When_UpdateCommentIdentifierIsInvalid(){
        Comment comment = TestUtilsComment.buildCommentMock();
        when(commentPersistencePort.findById(anyString())).thenReturn(Mono.empty());
        Mono<Comment> updatePost=commentService.update("1",comment);
        StepVerifier.create(updatePost)
                .expectError(CommentNotFoundException.class)
                .verify();
        Mockito.verify(commentPersistencePort,times(0)).save(any(Comment.class));
        Mockito.verify(commentPersistencePort,times(1)).findById(anyString());
    }

    @Test
    @DisplayName("When Comment Exists Expect Comment Deleted Successfully")
    void When_CommentExists_Expect_CommentDeletedSuccessfully() {
        Comment comment = TestUtilsComment.buildCommentMock();
        when(commentPersistencePort.findById(anyString())).thenReturn(Mono.just(comment));
        when(commentPersistencePort.delete(anyString())).thenReturn(Mono.empty());

        Mono<Void> result = commentService.delete("1");

        StepVerifier.create(result)
                .verifyComplete();

        Mockito.verify(commentPersistencePort, times(1)).findById(anyString());
        Mockito.verify(commentPersistencePort, times(1)).delete(anyString());
    }

    @Test
    @DisplayName("Expect CommentNotFoundException When Comment Does Not Exist")
    void Expect_CommentNotFoundException_When_CommentDoesNotExist() {
        when(commentPersistencePort.findById(anyString())).thenReturn(Mono.empty());

        Mono<Void> result = commentService.delete("1");

        StepVerifier.create(result)
                .expectError(CommentNotFoundException.class)
                .verify();

        Mockito.verify(commentPersistencePort, times(1)).findById(anyString());
        Mockito.verify(commentPersistencePort, Mockito.never()).delete(anyString());
    }

    @Test
    @DisplayName("When Post Identifier Is Correct Expect A List Of Comments")
    void When_PostIdentifierIsCorrect_Expect_AListOfComments() {
        Comment comment = TestUtilsComment.buildCommentMock();

        when(commentPersistencePort.findAllByPostId(anyString())).thenReturn(Flux.just(comment));

        Flux<Comment> comments = commentService.findAllByPostId("postId");
        StepVerifier.create(comments)
                .expectNext(comment)
                .verifyComplete();

        Mockito.verify(commentPersistencePort, times(1)).findAllByPostId(anyString());
    }

    @Test
    @DisplayName("Expect PostNotFoundException When Post Does Not Exist")
    void Expect_PostNotFoundException_When_PostDoesNotExist() {
        Comment comment = TestUtilsComment.buildCommentMock();
        when(externalPostOutputPort.verify(anyString())).thenReturn(Mono.just(false));

        Mono<Comment> savedComment = commentService.save(comment);

        StepVerifier.create(savedComment)
                .expectError(PostNotFoundException.class)
                .verify();
        Mockito.verify(externalPostOutputPort, times(1)).verify(anyString());
        Mockito.verify(commentPersistencePort, times(0)).save(any(Comment.class));
    }

    @Test
    @DisplayName("When Comment Verification Is Successful Expect Comment Verified")
    void When_UserVerificationIsSuccessful_Expect_UserVerified() {
        when(commentPersistencePort.verifyById(anyString())).thenReturn(Mono.just(true));

        Mono<Boolean> result = commentService.verifyById("6786d05449b7975d2e3c3626");

        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();

        Mockito.verify(commentPersistencePort, times(1)).verifyById(anyString());
    }

    @Test
    @DisplayName("When Post Identifier Is Correct Expect A List Of Comments With User Information")
    void When_PostIdentifierIsCorrect_Expect_AListOfCommentsWithUserInformation() {
        Comment comment = TestUtilsComment.buildCommentMock();

        when(commentPersistencePort.findAllByPostId(anyString())).thenReturn(Flux.just(comment));

        Flux<Comment> comments = commentService.findAllByPostId("postId");

        StepVerifier.create(comments)
                .expectNext(comment)
                .verifyComplete();

        Mockito.verify(commentPersistencePort, times(1)).findAllByPostId(anyString());
    }

}
