package com.fernando.ms.comments.app.infrastructure.adapter.output.restclient;

import com.fernando.ms.comments.app.infraestructure.adapter.output.restclient.PostRestClientAdapter;
import com.fernando.ms.comments.app.infraestructure.adapter.output.restclient.UserRestClientAdapter;
import com.fernando.ms.comments.app.infraestructure.adapter.output.restclient.client.PostWebClient;
import com.fernando.ms.comments.app.infraestructure.adapter.output.restclient.client.UserWebClient;
import com.fernando.ms.comments.app.infraestructure.adapter.output.restclient.models.response.ExistsPostResponse;
import com.fernando.ms.comments.app.infraestructure.adapter.output.restclient.models.response.ExistsUserResponse;
import com.fernando.ms.comments.app.utils.TestUtilsPost;
import com.fernando.ms.comments.app.utils.TestUtilsUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostRestClientAdapterTest {
    @Mock
    private PostWebClient postWebClient;

    @InjectMocks
    private PostRestClientAdapter postRestClientAdapter;

    @Test
    @DisplayName("When PostId Is Correct Expect True if Post Exists")
    void When_PostIdIsCorrect_Expect_TrueIfPostExists(){
        when(postWebClient.verify(anyString())).thenReturn(Mono.just(TestUtilsPost.buildExistsPostResponseMock()));

        Mono<Boolean> exists=postRestClientAdapter.verify("556621d65s26d");

        StepVerifier.create(exists)
                .expectNext(true)
                .verifyComplete();
        Mockito.verify(postWebClient,times(1)).verify(anyString());
    }

    @Test
    @DisplayName("When PostId Is Not Correct Expect False if Post Not Exists")
    void When_PostIdIsNotCorrect_Expect_FalseIfPostNotExists(){
        ExistsPostResponse existsPostResponse=TestUtilsPost.buildExistsPostResponseMock();
        existsPostResponse.setExists(false);
        when(postWebClient.verify(anyString())).thenReturn(Mono.just(existsPostResponse));
        Mono<Boolean> exists=postRestClientAdapter.verify("556621d65s26d");

        StepVerifier.create(exists)
                .expectNext(false)
                .verifyComplete();
        Mockito.verify(postWebClient,times(1)).verify(anyString());
    }


}
