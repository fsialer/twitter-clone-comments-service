package com.fernando.ms.comments.app.infraestructure.adapter.output.restclient.client;

import com.fernando.ms.comments.app.infraestructure.adapter.output.restclient.models.response.ExistsUserResponse;
import com.fernando.ms.comments.app.infraestructure.adapter.output.restclient.models.response.UserResponse;
import reactor.core.publisher.Mono;

public interface UserWebClient {
    Mono<ExistsUserResponse> verify(Long id);
    Mono<UserResponse> findById(Long id);
}
