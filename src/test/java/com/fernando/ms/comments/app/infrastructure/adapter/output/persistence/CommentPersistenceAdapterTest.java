package com.fernando.ms.comments.app.infrastructure.adapter.output.persistence;

import com.fernando.ms.comments.app.domain.models.Comment;
import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.CommentPersistenceAdapter;
import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.Models.CommentDocument;
import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.Models.CommentPost;
import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.mapper.CommentPersistenceMapper;
import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.repository.CommentReactiveMongoRepository;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentPersistenceAdapterTest {
    @Mock
    private CommentReactiveMongoRepository commentReactiveMongoRepository;

    @Mock
    private CommentPersistenceMapper commentPersistenceMapper;

    @InjectMocks
    private CommentPersistenceAdapter commentPersistenceAdapter;

    @Test
    @DisplayName("When Comments Are Correct Expect A List Comments Correct")
    void When_CommentsAreCorrect_Expect_AListCommentsCorrect() {
        Comment comment= TestUtilsComment.buildCommentMock();
        CommentDocument commentDocument= TestUtilsComment.buildCommentDocumentMock();
        when(commentReactiveMongoRepository.findAll()).thenReturn(Flux.just(commentDocument));
        when(commentPersistenceMapper.toComments(any(Flux.class))).thenReturn(Flux.just(comment));
        Flux<Comment> result = commentPersistenceAdapter.findAll();
        StepVerifier.create(result)
                .expectNext(comment)
                .verifyComplete();
        Mockito.verify(commentReactiveMongoRepository,times(1)).findAll();
        Mockito.verify(commentPersistenceMapper,times(1)).toComments(any(Flux.class));
    }

    @Test
    @DisplayName("When Comment Identifier Is Correct Expect Comment Information Correct")
    void When_CommentIdentifierIsCorrect_Expect_CommentInformationCorrect(){
        Comment comment= TestUtilsComment.buildCommentMock();
        CommentDocument commentDocument= TestUtilsComment.buildCommentDocumentMock();
        when(commentReactiveMongoRepository.findById(anyString())).thenReturn(Mono.just(commentDocument));
        when(commentPersistenceMapper.toComment(any(CommentDocument.class))).thenReturn(comment);

        Mono<Comment> result=commentPersistenceAdapter.findById("1");
        StepVerifier.create(result)
                .expectNext(comment)
                .verifyComplete();
        Mockito.verify(commentReactiveMongoRepository,times(1)).findById(anyString());
        Mockito.verify(commentPersistenceMapper,times(1)).toComment(any(CommentDocument.class));
    }

    @Test
    @DisplayName("When Comment Is Saved Successfully Expect Comment Information Correct")
    void When_CommentIsSavedSuccessfully_Expect_CommentInformationCorrect() {
        Comment comment = TestUtilsComment.buildCommentMock();
        CommentDocument commentDocument = TestUtilsComment.buildCommentDocumentMock();

        when(commentPersistenceMapper.toCommentDocument(any(Comment.class))).thenReturn(commentDocument);
        when(commentReactiveMongoRepository.save(any(CommentDocument.class))).thenReturn(Mono.just(commentDocument));
        when(commentPersistenceMapper.toComment(any(Mono.class))).thenReturn(Mono.just(comment));

        Mono<Comment> savedComment = commentPersistenceAdapter.save(comment);

        StepVerifier.create(savedComment)
                .expectNext(comment)
                .verifyComplete();
        Mockito.verify(commentPersistenceMapper, times(1)).toCommentDocument(any(Comment.class));
        Mockito.verify(commentReactiveMongoRepository, times(1)).save(any(CommentDocument.class));
        Mockito.verify(commentPersistenceMapper, times(1)).toComment(any(Mono.class));
    }

    @Test
    @DisplayName("When Comment Exists Expect Comment Deleted Successfully")
    void When_CommentExists_Expect_CommentDeletedSuccessfully() {
        when(commentReactiveMongoRepository.deleteById(anyString())).thenReturn(Mono.empty());
        Mono<Void> result = commentPersistenceAdapter.delete("1");
        StepVerifier.create(result)
                .verifyComplete();
        Mockito.verify(commentReactiveMongoRepository, times(1)).deleteById(anyString());
    }

    @Test
    @DisplayName("When Post Identifier Is Correct Expect A List Of Comments")
    void When_PostIdentifierIsCorrect_Expect_AListOfComments() {
        Comment comment = TestUtilsComment.buildCommentMock();
        CommentDocument commentDocument = TestUtilsComment.buildCommentDocumentMock();
        CommentPost commentPost = CommentPost.builder().postId("postId").build();

        when(commentReactiveMongoRepository.findAllByCommentPost(any(CommentPost.class))).thenReturn(Flux.just(commentDocument));
        when(commentPersistenceMapper.toComments(any(Flux.class))).thenReturn(Flux.just(comment));

        Flux<Comment> comments = commentPersistenceAdapter.findAllByPost("postId");

        StepVerifier.create(comments)
                .expectNext(comment)
                .verifyComplete();

        Mockito.verify(commentReactiveMongoRepository, times(1)).findAllByCommentPost(any(CommentPost.class));
        Mockito.verify(commentPersistenceMapper, times(1)).toComments(any(Flux.class));
    }

    @Test
    @DisplayName("When Comment Verification Is Successful Expect Comment Verified")
    void When_CommentVerificationIsSuccessful_Expect_CommentVerified() {
        when(commentReactiveMongoRepository.existsById(anyString())).thenReturn(Mono.just(true));
        Mono<Boolean> result = commentPersistenceAdapter.verifyById("6786d05449b7975d2e3c3626");
        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();
        Mockito.verify(commentReactiveMongoRepository, times(1)).existsById(anyString());
    }

}
