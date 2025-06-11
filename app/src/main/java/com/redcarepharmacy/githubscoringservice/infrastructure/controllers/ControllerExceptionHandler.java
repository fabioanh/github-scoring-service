package com.redcarepharmacy.githubscoringservice.infrastructure.controllers;

import com.redcarepharmacy.githubscoringservice.application.spi.GithubRepositoryClientException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(GithubRepositoryClientException.class)
    public ResponseEntity<Object> handleConflict(GithubRepositoryClientException ex, WebRequest request) {
        String bodyOfResponse = "Error communicating with the GitHub API. Please check the service configuration and ensure the GitHub API is accessible.";
        return super.handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
