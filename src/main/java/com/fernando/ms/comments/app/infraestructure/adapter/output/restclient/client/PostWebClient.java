package com.fernando.ms.comments.app.infraestructure.adapter.output.restclient.client;

import com.fernando.ms.comments.app.infraestructure.adapter.output.restclient.models.response.ExistsPostResponse;
import reactor.core.publisher.Mono;

public interface PostWebClient {
    Mono<ExistsPostResponse> verify(String id);
}
