package com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCommentRequest {
    @NotBlank(message = "Field content cannot be null or blank")
    private String content;
    @NotBlank(message = "Field postId cannot be null or blank")
    private String postId;
    private String parentComment;
}
