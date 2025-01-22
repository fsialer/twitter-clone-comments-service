package com.fernando.ms.comments.app.infraestructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient webClientUser(WebClient.Builder builder) {
        return builder.baseUrl("http://localhost:8080").build();
    }

    @Bean
    public WebClient webClientPost(WebClient.Builder builder) {
        return builder.baseUrl("http://localhost:8081").build();
    }
}
