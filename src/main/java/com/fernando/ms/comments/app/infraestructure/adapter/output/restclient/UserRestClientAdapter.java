package com.fernando.ms.comments.app.infraestructure.adapter.output.restclient;

import com.fernando.ms.comments.app.application.ports.output.ExternalUserOutputPort;
import com.fernando.ms.comments.app.domain.models.User;
import com.fernando.ms.comments.app.infraestructure.adapter.output.restclient.mapper.UserRestClientMapper;
import com.fernando.ms.comments.app.infraestructure.adapter.output.restclient.models.response.ExistsUserResponse;
import com.fernando.ms.comments.app.infraestructure.adapter.output.restclient.models.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserRestClientAdapter implements ExternalUserOutputPort {
    private final WebClient webClientUser;
    private final UserRestClientMapper userRestClientMapper;
    @Override
    public Mono<Boolean> verify(Long id) {
        return webClientUser
                .get()
                .uri("/users/{id}/verify",id)
                .retrieve()
                .bodyToMono(ExistsUserResponse.class)
                .flatMap(existsUserResponse -> {
                    return Mono.just(existsUserResponse.getExists());
                });
    }

    @Override
    public Mono<User> findById(Long id) {
        return webClientUser
                .get()
                .uri("/users/{id}",id)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .flatMap(userResponse->{
                    return Mono.just(userRestClientMapper.toUser(userResponse));
                });
    }


}
