package com.fernando.ms.comments.app.infraestructure.adapter.input.rest.mapper;

import com.fernando.ms.comments.app.domain.models.Comment;
import com.fernando.ms.comments.app.domain.models.Post;
import com.fernando.ms.comments.app.domain.models.User;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.request.CreateCommentRequest;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.request.UpdateCommentRequest;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.response.CommentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import reactor.core.publisher.Flux;

@Mapper(componentModel = "spring")
public interface CommentRestMapper {
    default Flux<CommentResponse> toCommentsResponse(Flux<Comment> comments){
        return comments.map(this::toCommentResponse);
    }

    CommentResponse toCommentResponse(Comment comment);

    @Mapping(target = "user",expression = "java(mapUser(rq))")
    @Mapping(target = "post",expression = "java(mapPost(rq))")
    Comment toComment(CreateCommentRequest rq);

    Comment toComment(UpdateCommentRequest rq);

    default User mapUser(CreateCommentRequest rq){
        return User.builder()
                .id(rq.getUserId())
                .build();
    }

    default Post mapPost(CreateCommentRequest rq){
        return Post.builder()
                .id(rq.getPostId())
                .build();
    }
}
