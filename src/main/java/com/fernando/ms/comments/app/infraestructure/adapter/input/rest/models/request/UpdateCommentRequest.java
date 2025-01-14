package com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCommentRequest {
    @NotBlank(message = "Field content cannot be null or blank")
    private String content;
}
