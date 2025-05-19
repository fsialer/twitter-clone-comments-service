package com.fernando.ms.comments.app.infraestructure.adapter.output.restclient.model.response;

import lombok.Builder;

@Builder
public record UserResponse(String id,String names,String lastNames) {
}
