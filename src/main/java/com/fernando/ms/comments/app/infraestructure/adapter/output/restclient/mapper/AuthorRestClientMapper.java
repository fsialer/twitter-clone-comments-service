package com.fernando.ms.comments.app.infraestructure.adapter.output.restclient.mapper;

import com.fernando.ms.comments.app.domain.models.Author;
import com.fernando.ms.comments.app.infraestructure.adapter.output.restclient.model.response.UserResponse;
import org.mapstruct.Mapper;
import reactor.core.publisher.Mono;

@Mapper(componentModel = "spring")
public interface AuthorRestClientMapper {

    Author toAuthor(UserResponse userResponse);

    default Mono<Author> toMonoAuthor(Mono<UserResponse> userResponseMono) {
        return userResponseMono.map(this::toAuthor);
    }
}
