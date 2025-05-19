package com.fernando.ms.comments.app.utils;

import com.fernando.ms.comments.app.infraestructure.adapter.output.restclient.model.response.UserResponse;

public class TestUtilUser {
    public static UserResponse buildUserResponseMock(){
        return UserResponse.builder()
                .id("5d4d75sd4sd6sd")
                .names("Fernando")
                .lastNames("Sialer")
                .build();
    }
}
