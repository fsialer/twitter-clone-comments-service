package com.fernando.ms.comments.app.infraestructure.adapter.output.restclient.client.impl;

import com.fernando.ms.comments.app.domain.exception.FallBackException;
import com.fernando.ms.comments.app.infraestructure.adapter.output.restclient.client.PostWebClient;
import com.fernando.ms.comments.app.infraestructure.adapter.output.restclient.models.response.ExistsPostResponse;

import com.fernando.ms.comments.app.infraestructure.config.ServiceProperties;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class PostWebClientImpl implements PostWebClient {
    private final WebClient webClient;
    private final CircuitBreaker circuitBreaker;

    public PostWebClientImpl(CircuitBreakerRegistry circuitBreakerRegistry,ServiceProperties serviceProperties){
        this.webClient = WebClient.builder().baseUrl(serviceProperties.getPostsService()).build();
        this.circuitBreaker = circuitBreakerRegistry.circuitBreaker("postsServiceCB");
    }

    @Override
    public Mono<ExistsPostResponse> verify(String id) {
        return
                webClient
                        .get()
                        .uri("/{id}/verify",id)
                        .retrieve()
                        .bodyToMono(ExistsPostResponse.class)
                        .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
                        .onErrorResume(throwable -> Mono.error(new FallBackException()));
    }
}
