package com.fernando.ms.comments.app.infrastructure.adapter.output.persistence;

import com.fernando.ms.comments.app.domain.models.Comment;
import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.CommentPersistenceAdapter;
import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.Models.CommentDocument;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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

}
