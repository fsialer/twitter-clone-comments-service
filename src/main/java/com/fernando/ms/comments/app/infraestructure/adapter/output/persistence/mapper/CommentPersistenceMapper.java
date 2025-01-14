package com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.mapper;

import com.fernando.ms.comments.app.domain.models.Comment;
import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.Models.CommentDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface CommentPersistenceMapper {
    default Flux<Comment> toComments(Flux<CommentDocument> comments) {
        return comments.map(this::toComment);
    }

    default Mono<Comment> toComment(Mono<CommentDocument> comment) {
        return comment.map(this::toComment);
    }

    Comment toComment(CommentDocument post);

    @Mapping(target = "dateComment",expression = "java(mapDateComment())")
    @Mapping(target = "createdAt",expression = "java(mapCreatedAt())")
    @Mapping(target = "updatedAt",expression = "java(mapUpdatedAt())")
    CommentDocument toCommentDocument(Comment comment);

    default LocalDateTime mapDateComment(){
        return LocalDateTime.now();
    }

    default LocalDateTime mapCreatedAt(){
        return LocalDateTime.now();
    }

    default LocalDateTime mapUpdatedAt(){
        return LocalDateTime.now();
    }
}
