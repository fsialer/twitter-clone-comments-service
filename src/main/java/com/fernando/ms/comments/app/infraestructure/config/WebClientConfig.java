package com.fernando.ms.comments.app.infraestructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Value("${posts-service.url}")
    private String apiPost;

    @Bean
    public WebClient webClientPost(WebClient.Builder builder) {
        return builder.baseUrl(apiPost).build();
    }
}
