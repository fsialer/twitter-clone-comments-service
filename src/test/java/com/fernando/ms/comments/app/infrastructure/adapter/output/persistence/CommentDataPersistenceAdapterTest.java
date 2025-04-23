package com.fernando.ms.comments.app.infrastructure.adapter.output.persistence;

import com.fernando.ms.comments.app.domain.models.CommentData;
import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.CommentDataPersistenceAdapter;
import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.Models.CommentDataDocument;
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
public class CommentDataPersistenceAdapterTest {
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
}
