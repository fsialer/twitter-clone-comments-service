package com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "comments_data")
@EqualsAndHashCode
public class CommentDataDocument {
    @Id
    private String id;
    @Indexed
    private String commentId;
    private String typeTarget;
    @Indexed
    private String userId;
    private LocalDateTime createdAt;
}
