package com.fernando.ms.comments.app.infrastructure.adapter.output.persistence;

import com.fernando.ms.comments.app.domain.models.CommentData;
import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.CommentDataPersistenceAdapter;
import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.model.CommentDataDocument;
import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.mapper.CommentDataPersistenceMapper;
import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.repository.CommentDataRepository;
import com.fernando.ms.comments.app.utils.TestUtilCommentData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class CommentDataPersistenceAdapterTest {
    @Mock
    private CommentDataRepository commentDataRepository;

    @Mock
    private CommentDataPersistenceMapper commentDataPersistenceMapper;

    @InjectMocks
    private CommentDataPersistenceAdapter commentDataPersistenceAdapter;

    @Test
    @DisplayName("When CommentData is correct Expect CommentData saved correctly")
    void When_CommentDataIsCorrect_Expect_CommentDataSavedCorrectly() {
        CommentData commentData= TestUtilCommentData.buildCommentDataMock();
        CommentDataDocument commentDataDocument= TestUtilCommentData.buildCommentDataDocumentMock();
        when(commentDataPersistenceMapper.toCommentDataDocument(any(CommentData.class))).thenReturn(commentDataDocument);
        when(commentDataRepository.save(any(CommentDataDocument.class))).thenReturn(Mono.just(commentDataDocument));
        when(commentDataPersistenceMapper.toCommentData(any(Mono.class))).thenReturn(Mono.just(commentData));

        Mono<Void> result = commentDataPersistenceAdapter.save(commentData);

        StepVerifier.create(result)
                .verifyComplete();

        verify(commentDataRepository, times(1)).save(any(CommentDataDocument.class));
        verify(commentDataPersistenceMapper, times(1)).toCommentDataDocument(any(CommentData.class));
    }

    @Test
    @DisplayName("when Verify CommentData Exists Expect True")
    void When_VerifyCommentDataExists_Expect_True(){
        String postId = "postId123";
        String userId = "userId123";

        when(commentDataRepository.existsByCommentIdAndUserId(anyString(), anyString())).thenReturn(Mono.just(true));

        Mono<Boolean> result = commentDataPersistenceAdapter.verifyCommentData(postId, userId);

        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();

        verify(commentDataRepository, times(1)).existsByCommentIdAndUserId(anyString(), anyString());
    }

    @Test
    @DisplayName("When Identifier of the comment is correct Expect Return CommentData saved correctly")
    void When_IdentifierOfTheCommentIsCorrect_Expect_ReturnCommentDataSavedCorrectly() {
        String commentId = "commentId123";
        CommentDataDocument commentDataDocument = TestUtilCommentData.buildCommentDataDocumentMock();
        CommentData commentData = TestUtilCommentData.buildCommentDataMock();

        when(commentDataRepository.findById(anyString())).thenReturn(Mono.just(commentDataDocument));
        when(commentDataPersistenceMapper.toCommentData(any(Mono.class))).thenReturn(Mono.just(commentData));

        Mono<CommentData> result = commentDataPersistenceAdapter.findById(commentId);

        StepVerifier.create(result)
                .expectNext(commentData)
                .verifyComplete();

        verify(commentDataRepository, times(1)).findById(anyString());
        verify(commentDataPersistenceMapper, times(1)).toCommentData(any(Mono.class));
    }

    @Test
    @DisplayName("when Delete By Id Expect Complete Successfully")
    void when_DeleteById_Expect_CompleteSuccessfully() {
        String commentId = "commentId123";
        when(commentDataRepository.deleteById(commentId)).thenReturn(Mono.empty());
        Mono<Void> result = commentDataPersistenceAdapter.delete(commentId);
        StepVerifier.create(result)
                .verifyComplete();

        verify(commentDataRepository, times(1)).deleteById(commentId);
    }

    @Test
    @DisplayName("When CommentId Exits Expect Quantity Comments Data")
    void When_CommentIdExists_Expect_CountCommentsData(){
        when(commentDataRepository.countCommentDataByCommentId(anyString())).thenReturn(Mono.just(2L));
        Mono<Long> result=commentDataPersistenceAdapter.countCommentDataByComment("1");

        StepVerifier.create(result)
                .expectNext(2L)
                .verifyComplete();
        verify(commentDataRepository,times(1)).countCommentDataByCommentId(anyString());
    }

    @Test
    @DisplayName("When Identifier of the comment And User is correct Expect Return CommentData saved correctly")
    void When_IdentifierOfTheCommentAndUserIsCorrect_Expect_ReturnCommentDataSavedCorrectly() {
        String commentId = "commentId123";
        String userId="userId1254";
        CommentDataDocument commentDataDocument = TestUtilCommentData.buildCommentDataDocumentMock();
        CommentData commentData = TestUtilCommentData.buildCommentDataMock();

        when(commentDataRepository.findByCommentIdAndUserId(anyString(),anyString())).thenReturn(Mono.just(commentDataDocument));
        when(commentDataPersistenceMapper.toCommentData(any(Mono.class))).thenReturn(Mono.just(commentData));

        Mono<CommentData> result = commentDataPersistenceAdapter.findByCommentIdAndUserId(commentId,userId);

        StepVerifier.create(result)
                .expectNext(commentData)
                .verifyComplete();

        verify(commentDataRepository, times(1)).findByCommentIdAndUserId(anyString(),anyString());
        verify(commentDataPersistenceMapper, times(1)).toCommentData(any(Mono.class));
    }
}
