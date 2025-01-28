package com.fernando.ms.comments.app.infraestructure.adapter.output.restclient;

import com.fernando.ms.comments.app.application.ports.output.ExternalUserOutputPort;
import com.fernando.ms.comments.app.domain.models.User;
import com.fernando.ms.comments.app.infraestructure.adapter.output.restclient.client.UserWebClient;
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

    private final UserWebClient userWebClient;
    private final UserRestClientMapper userRestClientMapper;
    @Override
    public Mono<Boolean> verify(Long id) {
        return userWebClient.verify(id)
                .flatMap(existsUserResponse -> {
                    return Mono.just(existsUserResponse.getExists());
                });
    }

    @Override
    public Mono<User> findById(Long id) {
        return userWebClient.findById(id)
                .flatMap(userResponse->{
                    return Mono.just(userRestClientMapper.toUser(userResponse));
                });
    }


}
