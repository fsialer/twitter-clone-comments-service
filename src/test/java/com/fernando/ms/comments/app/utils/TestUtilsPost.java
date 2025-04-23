package com.fernando.ms.comments.app.utils;

import com.fernando.ms.comments.app.infraestructure.adapter.output.restclient.models.response.ExistsPostResponse;

public class TestUtilsPost {
    public static ExistsPostResponse buildExistsPostResponseMock(){
        return ExistsPostResponse.builder()
                .exists(true)
                .build();
    }

}
