package com.fernando.ms.comments.app.infraestructure.adapter.input.rest.mapper;

import com.fernando.ms.comments.app.domain.models.CommentData;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.request.CreateCommentDataRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentDataRestMapper {
    CommentData toCommentData(CreateCommentDataRequest createCommentDataRequest);

    default CommentData toCommentData(String userId,CreateCommentDataRequest createCommentDataRequest){
        return CommentData.builder()
                .commentId(createCommentDataRequest.getCommentId())
                .typeTarget(createCommentDataRequest.getTypeTarget())
                .userId(userId)
                .build();
    }
}
