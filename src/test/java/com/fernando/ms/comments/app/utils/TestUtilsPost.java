package com.fernando.ms.comments.app.utils;

import com.fernando.ms.comments.app.infraestructure.adapter.output.restclient.models.response.ExistsPostResponse;
import com.fernando.ms.comments.app.infraestructure.adapter.output.restclient.models.response.ExistsUserResponse;

public class TestUtilsPost {
    public static ExistsPostResponse buildExistsPostResponseMock(){
        return ExistsPostResponse.builder()
                .exists(true)
                .build();
    }

}
