package com.fernando.ms.comments.app.infraestructure.adapter.input.rest.mapper;

import com.fernando.ms.comments.app.domain.models.Comment;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.response.CommentResponse;
import org.mapstruct.Mapper;
import reactor.core.publisher.Flux;

@Mapper(componentModel = "spring")
public interface CommentRestMapper {
    default Flux<CommentResponse> toCommentsResponse(Flux<Comment> comments){
        return comments.map(this::toCommentResponse);
    }

    CommentResponse toCommentResponse(Comment comment);
}
