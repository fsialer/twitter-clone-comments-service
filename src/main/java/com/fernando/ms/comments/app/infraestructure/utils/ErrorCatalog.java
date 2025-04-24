package com.fernando.ms.comments.app.infraestructure.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCatalog {
    COMMENT_NOT_FOUND("COMMENT_MS_001", "Comment not found."),
    COMMENT_BAD_PARAMETERS("COMMENT_MS_002", "Invalid parameters for creation comment"),
    USER_NOT_FOUND("COMMENT_MS_003", "User not found."),
    POST_NOT_FOUND("COMMENT_MS_004", "Post not found."),
    COMMENT_RULE_EXCEPTION("COMMENT_MS_005","Rule invalid"),
    COMMENT_DATA_NOT_FOUND("COMMENT_MS_006","CommentData not found."),
    COMMENT_SERVICES_FAIL("COMMENT_MS_007","Service not available."),
    COMMENT_INTERNAL_SERVER_ERROR("COMMENT_MS_000", "Internal server error.");
    private final String code;
    private final String message;
}
