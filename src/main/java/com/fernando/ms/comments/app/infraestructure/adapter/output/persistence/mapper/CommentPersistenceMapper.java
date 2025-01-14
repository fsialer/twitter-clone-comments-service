package com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.mapper;

import com.fernando.ms.comments.app.domain.models.Comment;
import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.Models.CommentDocument;
import org.mapstruct.Mapper;
import reactor.core.publisher.Flux;

@Mapper(componentModel = "spring")
public interface CommentPersistenceMapper {
    default Flux<Comment> toComments(Flux<CommentDocument> comments) {
        return comments.map(this::toComment);
    }
    Comment toComment(CommentDocument post);
}
