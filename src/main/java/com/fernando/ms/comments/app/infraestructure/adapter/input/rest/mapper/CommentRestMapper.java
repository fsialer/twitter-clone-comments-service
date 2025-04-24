package com.fernando.ms.comments.app.infraestructure.adapter.input.rest.mapper;

import com.fernando.ms.comments.app.domain.models.Comment;
import com.fernando.ms.comments.app.domain.models.Post;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.request.CreateCommentRequest;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.request.UpdateCommentRequest;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.response.CommentResponse;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.response.CommentUserResponse;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.response.ExistsCommentResponse;
import org.mapstruct.Mapper;
import reactor.core.publisher.Flux;

@Mapper(componentModel = "spring")
public interface CommentRestMapper {
    default Flux<CommentResponse> toCommentsResponse(Flux<Comment> comments){
        return comments.map(this::toCommentResponse);
    }

    default Flux<CommentUserResponse> toCommentsUserResponse(Flux<Comment> comments){
        return comments.map(this::toCommentUserResponse);
    }

    CommentUserResponse toCommentUserResponse(Comment comment);

    CommentResponse toCommentResponse(Comment comment);

    Comment toComment(String userId,CreateCommentRequest rq);

    Comment toComment(UpdateCommentRequest rq);


    default Post mapPost(CreateCommentRequest rq){
        return Post.builder()
                .id(rq.getPostId())
                .build();
    }

    default ExistsCommentResponse toExistsCommentResponse(Boolean exists){
        return ExistsCommentResponse.builder()
                .exists(exists)
                .build();
    }
}
