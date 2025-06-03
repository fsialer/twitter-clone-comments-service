package com.fernando.ms.comments.app.infrastructure.adapter.output.persistence.repository;

import com.fernando.ms.comments.app.domain.models.Author;
import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.model.CommentDocument;
import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.repository.CommentRepositoryCustomImpl;
import com.fernando.ms.comments.app.utils.TestUtilAuthor;
import com.fernando.ms.comments.app.utils.TestUtilsComment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentRepositoryCustomImplTest {


    @Mock
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @InjectMocks
    private CommentRepositoryCustomImpl commentRepositoryCustom;

    @Test
    @DisplayName("When Find Comment By PostId And Pagination Expect List Of Comments")
    void When_FindCommentByPostIdAndPagination_Expect_ListOfComments() {
        Author author2 = TestUtilAuthor.buildAuthorMock();
        author2.setId("4d786ds8sd56sd");
        author2.setNames("John");
        author2.setLastNames("Doe");
        CommentDocument commentDocument1 = TestUtilsComment.buildCommentDocumentMock();
        CommentDocument commentDocument2 = TestUtilsComment.buildCommentDocumentMock();
        commentDocument2.setId("d854gorfd4");
        commentDocument2.setContent("contenido 2");

        when(reactiveMongoTemplate.find(any(Query.class), any(Class.class)))
                .thenReturn(Flux.just(commentDocument1, commentDocument2));

        Flux<CommentDocument> result = commentRepositoryCustom.findAllByPostId("1L", 1, 10);

        StepVerifier.create(result)
                .expectNext(commentDocument1)
                .expectNext(commentDocument2)
                .verifyComplete();
    }

    @Test
    @DisplayName("When PostId Exists Expect Quantity Comments")
    void When_PostIdExists_Expect_QuantityComments(){
        String postId = "user123";
        Long expectedCount = 5L;

        when(reactiveMongoTemplate.count(any(Query.class), eq(CommentDocument.class)))
                .thenReturn(Mono.just(expectedCount));

        Mono<Long> result = commentRepositoryCustom.countCommentByPostId(postId);

        StepVerifier.create(result)
                .expectNext(expectedCount)
                .verifyComplete();

        verify(reactiveMongoTemplate, times(1)).count(any(Query.class), eq(CommentDocument.class));
    }
}
