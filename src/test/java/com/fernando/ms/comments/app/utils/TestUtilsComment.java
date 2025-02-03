package com.fernando.ms.comments.app.utils;

import com.fernando.ms.comments.app.domain.models.Comment;
import com.fernando.ms.comments.app.domain.models.Post;
import com.fernando.ms.comments.app.domain.models.User;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.request.CreateCommentRequest;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.request.UpdateCommentRequest;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.response.CommentResponse;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.response.CommentUserResponse;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.response.ExistsCommentResponse;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.response.UserResponse;
import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.Models.CommentDocument;
import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.Models.CommentPost;
import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.Models.CommentUser;

import java.time.LocalDateTime;

public class TestUtilsComment {
    public static Comment buildCommentMock(){
        return Comment.builder()
                .id("1")
                .content("comment")
                .dateComment(LocalDateTime.now())
                .post(Post.builder()
                        .id("1")
                        .content("Helloworld")
                        .datePost(LocalDateTime.now())
                        .build())
                .user(User.builder()
                        .id(1L)
                        .names("fernando")
                        .build())
                .build();
    }

    public static CommentDocument buildCommentDocumentMock(){
        return CommentDocument.builder()
                .id("1")
                .content("comment")
                .dateComment(LocalDateTime.now())
                .commentPost(CommentPost.builder().postId("1").build())
                .commentUser(CommentUser.builder().userId(1L).build())
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
                .user(UserResponse.builder().id(1L).names("fernando").build())
                .build();
    }
}
