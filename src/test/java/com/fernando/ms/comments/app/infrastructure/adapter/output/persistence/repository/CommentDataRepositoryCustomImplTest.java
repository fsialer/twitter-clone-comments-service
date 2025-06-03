package com.fernando.ms.comments.app.infrastructure.adapter.output.persistence.repository;

import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.model.CommentDataDocument;
import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.repository.CommentDataRepositoryCustomImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentDataRepositoryCustomImplTest {
    @Mock
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @InjectMocks
    private CommentDataRepositoryCustomImpl commentDataRepositoryCustom;

    @Test
    @DisplayName("When CommentId Exists Expect Quantity Comments Data")
    void When_CommentIdExists_Expect_QuantityCommentsData(){
        String commentId = "user123";
        Long expectedCount = 5L;

        when(reactiveMongoTemplate.count(any(Query.class), eq(CommentDataDocument.class)))
                .thenReturn(Mono.just(expectedCount));

        Mono<Long> result = commentDataRepositoryCustom.countCommentDataByCommentId(commentId);

        StepVerifier.create(result)
                .expectNext(expectedCount)
                .verifyComplete();

        verify(reactiveMongoTemplate, times(1)).count(any(Query.class), eq(CommentDataDocument.class));
    }
}
