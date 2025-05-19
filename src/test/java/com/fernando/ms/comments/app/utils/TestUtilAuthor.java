package com.fernando.ms.comments.app.utils;

import com.fernando.ms.comments.app.domain.models.Author;

public class TestUtilAuthor {
    public static Author buildAuthorMock(){
        return Author.builder()
                .id("5d4d75sd4sd6sd")
                .names("Fernando")
                .lastNames("Sialer")
                .build();
    }
}
