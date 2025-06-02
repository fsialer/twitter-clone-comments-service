package com.fernando.ms.comments.app.infraestructure.adapter.output.restclient.client.impl;

import com.fernando.ms.comments.app.domain.exception.UserFallBackException;
import com.fernando.ms.comments.app.infraestructure.adapter.output.restclient.client.UserWebClient;
import com.fernando.ms.comments.app.infraestructure.adapter.output.restclient.model.response.UserResponse;
import com.fernando.ms.comments.app.infraestructure.config.ServiceProperties;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class UserWebClientImpl implements UserWebClient {
    private final WebClient webClient;
    private final CircuitBreaker circuitBreaker;
    public UserWebClientImpl(CircuitBreakerRegistry circuitBreakerRegistry,
                             WebClient.Builder webClientBuilder,
                             ServiceProperties serviceProperties){
        this.webClient =webClientBuilder.baseUrl(serviceProperties.getUsersService()).build();
        this.circuitBreaker = circuitBreakerRegistry.circuitBreaker("usersServiceCB");
    }
    @Override
    public Mono<UserResponse> findByUserId(String userId) {

        return webClient
                .get()
                .uri("/{userId}/user-id",userId)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
                .onErrorResume(throwable -> Mono.error(new UserFallBackException()));
    }
}
