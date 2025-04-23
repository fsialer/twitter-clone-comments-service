package com.fernando.ms.comments.app.domain.models;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {
    private String id;
    private String content;
    private LocalDateTime dateComment;
    private String userId;
    private String postId;
}
