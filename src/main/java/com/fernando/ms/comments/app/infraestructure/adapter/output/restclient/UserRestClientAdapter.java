package com.fernando.ms.comments.app.infraestructure.adapter.output.restclient;

import com.fernando.ms.comments.app.application.ports.output.ExternalUserOutputPort;
import com.fernando.ms.comments.app.domain.models.Author;
import com.fernando.ms.comments.app.infraestructure.adapter.output.restclient.client.UserWebClient;
import com.fernando.ms.comments.app.infraestructure.adapter.output.restclient.mapper.AuthorRestClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserRestClientAdapter implements ExternalUserOutputPort {
    private final UserWebClient userWebClient;
    private final AuthorRestClientMapper authorRestClientMapper;

    @Override
    public Mono<Author> findAuthorByUserId(String userId) {
        return authorRestClientMapper.toMonoAuthor(userWebClient.findByUserId(userId));
    }
}
