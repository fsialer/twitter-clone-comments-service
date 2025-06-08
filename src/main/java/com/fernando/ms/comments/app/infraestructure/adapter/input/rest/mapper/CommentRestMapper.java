package com.fernando.ms.comments.app.infraestructure.adapter.input.rest.mapper;

import com.fernando.ms.comments.app.domain.models.Comment;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.request.CreateCommentRequest;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.request.UpdateCommentRequest;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.response.CommentResponse;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.response.CommentUserResponse;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.response.CountCommentResponse;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.response.ExistsCommentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentRestMapper {
    default Flux<CommentResponse> toCommentsResponse(Flux<Comment> comments){
        return comments.map(this::toCommentResponse);
    }

    default Flux<CommentUserResponse> toCommentsAuthorResponse(Flux<Comment> comments){
        return comments.map(this::toCommentAuthorResponse);
    }

    default CommentUserResponse toCommentAuthorResponse(Comment comment){
        return CommentUserResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .dateComment(comment.getDateComment())
                .author(comment.getAuthor().getNames().concat(" ").concat(comment.getAuthor().getLastNames()==null?"":comment.getAuthor().getLastNames()).trim())
                .answers(comment.getAnswers().stream().toList())
                .build();
    }

    CommentResponse toCommentResponse(Comment comment);

    Comment toComment(String userId,CreateCommentRequest rq);

    Comment toComment(UpdateCommentRequest rq);

    default ExistsCommentResponse toExistsCommentResponse(Boolean exists){
        return ExistsCommentResponse.builder()
                .exists(exists)
                .build();
    }

    default Mono<CountCommentResponse> toCountCommentResponse(Long quantity){
        return Mono.just(CountCommentResponse.builder()
                .quantity(quantity)
                .build());
    }
}
