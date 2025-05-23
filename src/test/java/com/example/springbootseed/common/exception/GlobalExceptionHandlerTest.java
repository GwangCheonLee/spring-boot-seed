package com.example.springbootseed.common.exception;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.NoHandlerFoundException;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    void testHandleGenericException() {
        Exception testException = new Exception("Test exception message");
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleGenericException(
            testException);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).containsKeys("status", "error", "message");
        assertThat(response.getBody()).containsEntry("status",
            HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getBody()).containsEntry("error", "Internal Server Error");
        assertThat(response.getBody()).containsEntry("message",
            "An unexpected error occurred on the server.");
    }

    @Test
    void testHandleNoHandlerFoundException() {
        // Given
        NoHandlerFoundException noHandlerFoundException = new NoHandlerFoundException("GET",
            "/api/nonexistent", null);

        // When
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleNoHandlerFoundException(
            noHandlerFoundException);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).containsKeys("status", "error", "message");
        assertThat(response.getBody()).containsEntry("status", HttpStatus.NOT_FOUND.value());
        assertThat(response.getBody()).containsEntry("error", "Not Found");
        assertThat(response.getBody()).containsEntry("message",
            "The requested resource was not found.");
    }
}
