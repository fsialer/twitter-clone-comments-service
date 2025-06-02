package com.fernando.ms.comments.app.domain.models;

import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
    private String parentComment;
    @Builder.Default
    private Set<String> answers=new HashSet<>();
    private Author author;
}
