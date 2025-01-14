package com.fernando.ms.comments.app.application.services;

import com.fernando.ms.comments.app.application.ports.output.CommentPersistencePort;
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
import reactor.test.StepVerifier;

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

}
