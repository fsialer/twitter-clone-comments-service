package com.fernando.ms.comments.app.infraestructure.adapter.input.rest;

import com.fernando.ms.comments.app.domain.exception.*;
import com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.response.ErrorResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Collections;

import static com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.enums.ErrorType.FUNCTIONAL;
import static com.fernando.ms.comments.app.infraestructure.adapter.input.rest.models.enums.ErrorType.SYSTEM;
import static com.fernando.ms.comments.app.infraestructure.utils.ErrorCatalog.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@RestControllerAdvice
public class GlobalControllerAdvice {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CommentNotFoundException.class)
    public Mono<ErrorResponse> handleCommentNotFoundException() {
        return Mono.just(ErrorResponse.builder()
                .code(COMMENT_NOT_FOUND.getCode())
                .type(FUNCTIONAL)
                .message(COMMENT_NOT_FOUND.getMessage())
                .timestamp(LocalDate.now().toString())
                .build());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public Mono<ErrorResponse> handleUserNotFoundException() {
        return Mono.just(ErrorResponse.builder()
                .code(USER_NOT_FOUND.getCode())
                .type(FUNCTIONAL)
                .message(USER_NOT_FOUND.getMessage())
                .timestamp(LocalDate.now().toString())
                .build());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PostNotFoundException.class)
    public Mono<ErrorResponse> handlePostNotFoundException() {
        return Mono.just(ErrorResponse.builder()
                .code(POST_NOT_FOUND.getCode())
                .type(FUNCTIONAL)
                .message(POST_NOT_FOUND.getMessage())
                .timestamp(LocalDate.now().toString())
                .build());
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(CommentDataNotFoundException.class)
    public Mono<ErrorResponse> handleCommentDataNotFoundException() {
        return Mono.just(ErrorResponse.builder()
                .code(COMMENT_DATA_NOT_FOUND.getCode())
                .type(FUNCTIONAL)
                .message(COMMENT_DATA_NOT_FOUND.getMessage())
                .timestamp(LocalDate.now().toString())
                .build());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CommentRuleException.class)
    public Mono<ErrorResponse> handleCommentRuleException(
            CommentRuleException e) {
        return Mono.just(ErrorResponse.builder()
                .code(COMMENT_RULE_EXCEPTION.getCode())
                .type(FUNCTIONAL)
                .message(COMMENT_RULE_EXCEPTION.getMessage())
                .details(Collections.singletonList(e.getMessage()))
                .timestamp(LocalDate.now().toString())
                .build());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ErrorResponse> handleWebExchangeBindException(
            WebExchangeBindException e) {
        BindingResult bindingResult = e.getBindingResult();
        return Mono.just(ErrorResponse.builder()
                .code(COMMENT_BAD_PARAMETERS.getCode())
                .type(FUNCTIONAL)
                .message(COMMENT_BAD_PARAMETERS.getMessage())
                .details(bindingResult.getFieldErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList())
                .timestamp(LocalDate.now().toString())
                .build());
    }

    @ResponseStatus(SERVICE_UNAVAILABLE)
    @ExceptionHandler(FallBackException.class)
    public Mono<ErrorResponse> handleFallBackException(FallBackException e){
        return Mono.just(
                ErrorResponse.builder()
                        .code(COMMENT_SERVICES_FAIL.getCode())
                        .type(SYSTEM)
                        .message(COMMENT_SERVICES_FAIL.getMessage())
                        .timestamp(LocalDate.now().toString())
                        .build()
        );
    }

    @ResponseStatus(SERVICE_UNAVAILABLE)
    @ExceptionHandler(UserFallBackException.class)
    public Mono<ErrorResponse> handleUserFallBackException(UserFallBackException e){
        return Mono.just(
                ErrorResponse.builder()
                        .code(USERS_SERVICES_FAIL.getCode())
                        .type(SYSTEM)
                        .message(USERS_SERVICES_FAIL.getMessage())
                        .timestamp(LocalDate.now().toString())
                        .build()
        );
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Mono<ErrorResponse> handleException(Exception e) {

        return Mono.just(ErrorResponse.builder()
                .code(COMMENT_INTERNAL_SERVER_ERROR.getCode())
                .type(SYSTEM)
                .message(COMMENT_INTERNAL_SERVER_ERROR.getMessage())
                .details(Collections.singletonList(e.getMessage()))
                .timestamp(LocalDate.now().toString())
                .build());
    }
}
