package com.fernando.ms.comments.app.domain.models;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CommentData {
    private String id;
    private String commentId;
    private String typeTarget;
    private String userId;
}
