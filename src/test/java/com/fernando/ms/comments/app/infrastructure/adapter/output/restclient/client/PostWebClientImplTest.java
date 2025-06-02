package com.fernando.ms.comments.app.infrastructure.adapter.output.restclient.client;

import com.fernando.ms.comments.app.domain.exception.FallBackException;
import com.fernando.ms.comments.app.infraestructure.adapter.output.restclient.client.impl.PostWebClientImpl;
import com.fernando.ms.comments.app.infraestructure.adapter.output.restclient.models.response.ExistsPostResponse;
import com.fernando.ms.comments.app.infraestructure.config.ServiceProperties;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostWebClientImplTest {

    @Mock
    private WebClient webClient;

    private PostWebClientImpl postWebClient;

    @Mock
    private ServiceProperties serviceProperties;

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient.RequestHeadersUriSpec<?> requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec<?> requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;
    @Mock
    private CircuitBreakerRegistry circuitBreakerRegistry;

    @Test
    void test(){
        String postId = "123";
        ExistsPostResponse mockResponse = new ExistsPostResponse(true);

        when(webClientBuilder.baseUrl(anyString())).thenReturn(webClientBuilder);
        when(webClientBuilder.build()).thenReturn(webClient);
        Mockito.<WebClient.RequestHeadersUriSpec<?>>when(webClient.get()).thenReturn(requestHeadersUriSpec);
        Mockito.<WebClient.RequestHeadersSpec<?>>when(requestHeadersUriSpec.uri("/{id}/verify", postId)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(ExistsPostResponse.class)).thenReturn(Mono.just(mockResponse));
        when(serviceProperties.getPostsService()).thenReturn("http://fake-url");

        circuitBreakerRegistry = CircuitBreakerRegistry.ofDefaults();

        postWebClient = new PostWebClientImpl(circuitBreakerRegistry, webClientBuilder, serviceProperties);

        StepVerifier.create(postWebClient.verify(postId))
                .expectNextMatches(ExistsPostResponse::getExists)
                .verifyComplete();
    }

    @Test
    void verify_shouldReturnFallBackException_whenServiceFails() {
        String postId = "123";
        when(webClientBuilder.baseUrl(anyString())).thenReturn(webClientBuilder);
        when(webClientBuilder.build()).thenReturn(webClient);
        Mockito.<WebClient.RequestHeadersUriSpec<?>>when(webClient.get()).thenReturn(requestHeadersUriSpec);
        Mockito.<WebClient.RequestHeadersSpec<?>>when(requestHeadersUriSpec.uri("/{id}/verify", postId)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(ExistsPostResponse.class)).thenReturn(Mono.error(new FallBackException()));
        when(serviceProperties.getPostsService()).thenReturn("http://fake-url");

        circuitBreakerRegistry = CircuitBreakerRegistry.ofDefaults();
        postWebClient = new PostWebClientImpl(circuitBreakerRegistry, webClientBuilder, serviceProperties);

        StepVerifier.create(postWebClient.verify(postId))
                .expectError(FallBackException.class)
                .verify();
    }

}
