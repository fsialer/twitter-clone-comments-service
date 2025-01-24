package com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentUserResponse {
    private String id;
    private String content;
    private LocalDateTime dateComment;
    private  UserResponse user;
}
