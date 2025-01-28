package com.fernando.ms.comments.app.infraestructure.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {
    @Value("${users-service.url}")
    private final String apiUser;
    @Value("${posts-service.url}")
    private final String apiPost;

    @Bean
    public WebClient webClientUser(WebClient.Builder builder) {
        return builder.baseUrl(apiUser).build();
    }

    @Bean
    public WebClient webClientPost(WebClient.Builder builder) {
        return builder.baseUrl(apiPost).build();
    }
}
