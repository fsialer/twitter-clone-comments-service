package com.fernando.ms.comments.app.utils;

import com.fernando.ms.comments.app.domain.models.Author;
import com.fernando.ms.comments.app.domain.models.Comment;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.request.CreateCommentRequest;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.request.UpdateCommentRequest;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.response.*;
import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.model.CommentDocument;

import java.time.LocalDateTime;
import java.util.Set;

public class TestUtilsComment {
    public static Comment buildCommentAnswerMock(){
        return Comment.builder()
                .id("2")
                .content("comment")
                .dateComment(LocalDateTime.now())
                .postId("47fdfhhgds96574d")
                .userId("4uuydnsd4478954")
                .answers(Set.of("dsd6262d62s6d2s6d"))
                .build();
    }

    public static Comment buildCommentParentMock(){
        return Comment.builder()
                .id("2")
                .content("comment")
                .dateComment(LocalDateTime.now())
                .postId("47fdfhhgds96574d")
                .userId("4uuydnsd4478954")
                .parentComment("ds4557454dsd")
                .build();
    }

    public static Comment buildCommentMock(){
        return Comment.builder()
                .id("1")
                .content("comment")
                .dateComment(LocalDateTime.now())
                .postId("47fdfhhgds96574d")
                .userId("4uuydnsd4478954")
                .author(Author.builder()
                        .id("ds545d4sds")
                        .names("john")
                        .lastNames("doe")
                        .build())
                .build();
    }

    public static CommentDocument buildCommentDocumentMock(){
        return CommentDocument.builder()
                .id("1")
                .content("comment")
                .dateComment(LocalDateTime.now())
                .postId("47fdfhhgds96574d")
                .userId("4uuydnsd4478954")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                //.answers(Set.of(""))
                .build();
    }

    public static CommentResponse buildCommentResponseMock(){
        return CommentResponse.builder()
                .id("1")
                .content("comment")
                .dateComment(LocalDateTime.now())
                .build();
    }

    public static CreateCommentRequest buildCreateCommentRequestMock(){
        return CreateCommentRequest.builder()
                .content("comment")
                .postId("1")
                .dateComment(LocalDateTime.now().toString())
                .parentComment("3")
                .build();
    }

    public static UpdateCommentRequest buildUpdateCommentRequestMock(){
        return UpdateCommentRequest.builder()
                .content("comment")
                .build();
    }

    public static ExistsCommentResponse buildExistsCommentResponseMock(){
        return ExistsCommentResponse.builder()
                .exists(true)
                .build();
    }

    public static CommentUserResponse buildCommentUserResponseMock(){
        return CommentUserResponse.builder()
                .id("678daea99359a311d3e0035a")
                .content("comment")
                .dateComment(LocalDateTime.now())
                .author("john doe")
                .build();
    }

    public static CountCommentResponse buiildCountCommentResponseMock(){
        return CountCommentResponse.builder()
                .quantity(2L)
                .build();
    }

    public static CountCommentDataResponse buiildCountCommentDataResponseMock(){
        return CountCommentDataResponse.builder()
                .quantity(2L)
                .build();
    }
}
