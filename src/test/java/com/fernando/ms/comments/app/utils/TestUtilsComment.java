package com.fernando.ms.comments.app.utils;

import com.fernando.ms.comments.app.domain.models.Comment;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.request.CreateCommentRequest;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.request.UpdateCommentRequest;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.response.CommentResponse;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.response.CommentUserResponse;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.response.ExistsCommentResponse;
import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.model.CommentDocument;

import java.time.LocalDateTime;

public class TestUtilsComment {
    public static Comment buildCommentAnswerMock(){
        return Comment.builder()
                .id("2")
                .content("comment")
                .dateComment(LocalDateTime.now())
                .postId("47fdfhhgds96574d")
                .userId("4uuydnsd4478954")
                .answers(null)
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
                .userId("4uuydnsd4478954")
                .build();
    }
}
