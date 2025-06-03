package com.fernando.ms.comments.app.utils;

import com.fernando.ms.comments.app.domain.models.CommentData;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.request.CreateCommentDataRequest;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.response.ExistsCommentDataResponse;
import com.fernando.ms.comments.app.infraestructure.adapter.output.persistence.model.CommentDataDocument;

public class TestUtilCommentData {
    public static CommentData buildCommentDataMock(){
        return CommentData.builder()
                .id("1")
                .commentId("68045526dffe6e2de223e55b")
                .typeTarget("LIKE")
                .userId("fdsfds4544")
                .build();
    }

    public static CommentDataDocument buildCommentDataDocumentMock(){
        return CommentDataDocument.builder()
                .id("1")
                .commentId("68045526dffe6e2de223e55b")
                .typeTarget("LIKE")
                .userId("fdsfds4544")
                .build();
    }

    public static CreateCommentDataRequest buildCreateCommentDataRequestMock(){
        return CreateCommentDataRequest.builder()
                .commentId("68045526dffe6e2de223e55b")
                .typeTarget("LIKE")
                .build();
    }

    public static ExistsCommentDataResponse buildExistsCommentDataResponseMock(){
        return ExistsCommentDataResponse.builder()
                .exists(true)
                .build();
    }
}
