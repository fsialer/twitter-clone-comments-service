package com.fernando.ms.comments.app.infraestructure.adapter.output.restclient.models.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExistsUserResponse {
    private Boolean exists;
}
