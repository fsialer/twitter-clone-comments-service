package com.fernando.ms.comments.app.infraestructure.adapter.input.rest.mapper;

import com.fernando.ms.comments.app.domain.models.CommentData;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.request.CreateCommentDataRequest;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.response.CountCommentDataResponse;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.response.ExistsCommentDataResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import reactor.core.publisher.Mono;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentDataRestMapper {
    CommentData toCommentData(CreateCommentDataRequest createCommentDataRequest);

    default CommentData toCommentData(String userId,CreateCommentDataRequest createCommentDataRequest){
        return CommentData.builder()
                .commentId(createCommentDataRequest.getCommentId())
                .typeTarget(createCommentDataRequest.getTypeTarget())
                .userId(userId)
                .build();
    }

    default Mono<CountCommentDataResponse> toCountCommentDataResponse(Long quantity){
        return Mono.just(CountCommentDataResponse.builder()
                .quantity(quantity)
                .build());
    }

    default Mono<ExistsCommentDataResponse> toExistsCommentDataResponse(Boolean exists){
        return Mono.just(
                ExistsCommentDataResponse.builder()
                        .exists(exists)
                        .build()
        );
    }
}
