package com.fernando.ms.comments.app.infraestructure.adapter.output.restclient;

import com.fernando.ms.comments.app.application.ports.output.ExternalPostOutputPort;
import com.fernando.ms.comments.app.infraestructure.adapter.output.restclient.models.response.ExistsUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PostRestClientAdapter implements ExternalPostOutputPort {
    private final WebClient webClientPost;
    @Override
    public Mono<Boolean> verify(String id) {
        return  webClientPost
                .get()
                .uri("/posts/{id}/verify",id)
                .retrieve()
                .bodyToMono(ExistsUserResponse.class)
                .flatMap(existsPostResponse -> {
                    return Mono.just(existsPostResponse.getExists());
                });
    }
}
