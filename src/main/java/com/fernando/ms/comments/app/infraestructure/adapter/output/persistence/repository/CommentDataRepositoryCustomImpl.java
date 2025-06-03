package com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.repository;

import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.model.CommentDataDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CommentDataRepositoryCustomImpl implements CommentDataRepositoryCustom{

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    @Override
    public Mono<Long> countCommentDataByCommentId(String commentId) {
        Query query=new Query(Criteria.where("commentId").is(commentId));
        return reactiveMongoTemplate.count(query, CommentDataDocument.class);
    }
}
