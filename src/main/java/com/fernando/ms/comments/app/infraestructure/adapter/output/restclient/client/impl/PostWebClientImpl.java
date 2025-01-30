package com.fernando.ms.comments.app.infraestructure.adapter.output.restclient.client.impl;

import com.fernando.ms.comments.app.infraestructure.adapter.output.restclient.client.PostWebClient;
import com.fernando.ms.comments.app.infraestructure.adapter.output.restclient.models.response.ExistsPostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PostWebClientImpl implements PostWebClient {
    private final WebClient webClientPost;

    @Override
    public Mono<ExistsPostResponse> verify(String id) {
        return webClientPost
                .get()
                .uri("/{id}/verify",id)
                .retrieve()
                .bodyToMono(ExistsPostResponse.class);
    }
}
