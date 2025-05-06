package com.strange.jay.floor.floorplanservice.controllers;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

/** Log if a REST calls encounters an issue. */
@ControllerAdvice
public class RestExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        LOGGER.info("Exception making REST call: {}", e.getMessage());
        return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<Object> handleAccessDeniedException(
        final Exception ex, final WebRequest request) {
        return new ResponseEntity<Object>(
            "Access denied message here", new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({ NoSuchElementException.class })
    public ResponseEntity<Object> handleNoSuchElementException(
        final Exception ex, final WebRequest request) {
        return new ResponseEntity<Object>(
            "The requested floor plan or reference camera ID were not found. Please verify the input parameters.",
            new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<Object> handleValidationException(final HandlerMethodValidationException ex, final WebRequest request) {
        final List<String> errorList = ex.getAllErrors().stream()
            .map(MessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.toList());
        LOGGER.info("Validation exception: {}", errorList);
        return ResponseEntity.badRequest().body(errorList);
    }

}
