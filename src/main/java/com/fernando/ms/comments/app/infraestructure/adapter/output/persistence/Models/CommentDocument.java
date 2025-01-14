package com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.Models;

import com.fernando.ms.comments.app.domain.models.Post;
import com.fernando.ms.comments.app.domain.models.User;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "comments")
public class CommentDocument {
    @Id
    private String id;
    private String content;
    private LocalDateTime dateComment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private CommentUser commentUser;
    private CommentPost commentPost;
}
