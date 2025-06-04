package com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.mapper;

import com.fernando.ms.comments.app.domain.models.CommentData;
import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.model.CommentDataDocument;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import reactor.core.publisher.Mono;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentDataPersistenceMapper {
    default Mono<CommentData> toCommentData(Mono<CommentDataDocument> commentDataDocument){
        return commentDataDocument.map(this::toCommentData);
    }
    CommentData toCommentData(CommentDataDocument commentDataDocument);
    CommentDataDocument toCommentDataDocument(CommentData commentData);
}
