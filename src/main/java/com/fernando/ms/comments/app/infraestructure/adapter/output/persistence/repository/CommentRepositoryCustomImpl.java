package com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.repository;

import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.model.CommentDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom{

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    @Override
    public Flux<CommentDocument> findAllByPostId(String postId, int page, int size) {

        Query query=new Query(Criteria.where("postId").is(postId).and("parent").isNull());
        query.with(Sort.by(Sort.Direction.DESC,"datePost"))
                .skip((long) (page-1)*size)
                .limit(size);
        return reactiveMongoTemplate.find(query, CommentDocument.class);
    }

    @Override
    public Mono<Long> countCommentByPostId(String postId) {
        Query query=new Query(Criteria.where("postId").is(postId));
        return reactiveMongoTemplate.count(query,CommentDocument.class);
    }
}
