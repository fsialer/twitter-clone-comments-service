package com.fernando.ms.comments.app.infraestructure.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCatalog {
    COMMENT_NOT_FOUND("COMMENT_MS_001", "Comment not found."),
    COMMENT_BAD_PARAMETERS("COMMENT_MS_002", "Invalid parameters for creation comment"),
    INTERNAL_SERVER_ERROR("COMMENT_MS_000", "Internal server error.");
    private final String code;
    private final String message;
}
