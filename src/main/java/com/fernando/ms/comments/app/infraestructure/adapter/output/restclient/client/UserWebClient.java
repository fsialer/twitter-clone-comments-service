package com.fernando.ms.comments.app.infraestructure.adapter.output.restclient.client;

import com.fernando.ms.comments.app.infraestructure.adapter.output.restclient.model.response.UserResponse;
import reactor.core.publisher.Mono;

public interface UserWebClient {
    Mono<UserResponse> findByUserId(String userId);
}
