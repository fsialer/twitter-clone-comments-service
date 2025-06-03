package com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.repository;

import reactor.core.publisher.Mono;

public interface CommentDataRepositoryCustom {
    Mono<Long> countCommentDataByCommentId(String commentId);
}
