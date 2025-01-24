package com.fernando.ms.comments.app.utils;

import com.fernando.ms.comments.app.domain.models.User;

public class TestUtilsUser {
    public static User buildUserMock(){
        return User.builder()
                .id(1L)
                .names("Fernando")
                .build();
    }
}
