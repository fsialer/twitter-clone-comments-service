package com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

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
    @NotNull(message = "Field dateComment cannot be null")
    private LocalDateTime dateComment;
    private String parentComment;
}
