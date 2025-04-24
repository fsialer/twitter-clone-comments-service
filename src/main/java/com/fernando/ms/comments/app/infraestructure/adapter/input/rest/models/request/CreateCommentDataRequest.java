package com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.request;

import com.fernando.ms.comments.app.domain.enums.TypeTarget;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.request.validation.EnumValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCommentDataRequest {
    @NotBlank(message = "Field commentId cannot be null or blank")
    private String commentId;
    @NotNull(message = "Field typeTarget cannot be null or blank")
    @EnumValidator(enumClass = TypeTarget.class, message = "TypeTarget not valid")
    private String typeTarget;
}
