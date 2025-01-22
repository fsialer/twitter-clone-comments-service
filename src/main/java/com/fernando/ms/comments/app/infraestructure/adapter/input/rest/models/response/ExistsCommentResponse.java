package com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExistsCommentResponse {
    private Boolean exists;
}
