package com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String names;
    private String email;
    private String username;
}
