package com.fernando.ms.comments.app.utils;

import com.fernando.ms.comments.app.domain.models.User;
import com.fernando.ms.comments.app.infraestructure.adapter.output.restclient.models.response.ExistsUserResponse;
import com.fernando.ms.comments.app.infraestructure.adapter.output.restclient.models.response.UserResponse;

public class TestUtilsUser {
    public static User buildUserMock(){
        return User.builder()
                .id(1L)
                .names("Fernando")
                .build();
    }

    public static UserResponse buildUserResponseMock(){
        return UserResponse.builder()
                .id(1L)
                .names("fernando")
                .username("falex")
                .email("example@mail.com")
                .build();
    }

    public static ExistsUserResponse buildExistsUserResponseMock(){
        return ExistsUserResponse.builder()
                .exists(true)
                .build();
    }
}
