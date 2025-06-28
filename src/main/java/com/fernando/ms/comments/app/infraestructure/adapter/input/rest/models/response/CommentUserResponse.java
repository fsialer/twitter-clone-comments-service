package com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentUserResponse {
    private String id;
    private String content;
    private LocalDateTime dateComment;
    private String author;
    private String parentComment;
    private List<String> answers;
}
