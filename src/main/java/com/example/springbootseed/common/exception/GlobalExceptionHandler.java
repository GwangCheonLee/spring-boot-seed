package com.example.springbootseed.common.exception;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 전역 예외 처리 클래스 Spring Boot 애플리케이션에서 발생하는 예외를 JSON 형태로 응답합니다.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String STATUS = "status";
    private static final String ERROR = "error";
    private static final String MESSAGE = "message";


    /**
     * 404 Not Found 예외 처리 존재하지 않는 URL 요청 시 JSON 형태로 응답합니다.
     *
     * @param ex NoHandlerFoundException
     * @return ResponseEntity (JSON 형태의 404 에러 응답)
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, Object>> handleNoHandlerFoundException(
        NoHandlerFoundException ex) {
        log.warn("404 Not Found - No mapping found for request: {}", ex.getRequestURL());

        Map<String, Object> body = new HashMap<>();
        body.put(STATUS, HttpStatus.NOT_FOUND.value());
        body.put(ERROR, "Not Found");
        body.put(MESSAGE, "The requested resource was not found.");

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    /**
     * 500 Internal Server Error 예외 처리 서버 내부에서 발생하는 모든 예외를 처리합니다.
     *
     * @param ex Exception
     * @return ResponseEntity (JSON 형태의 에러 응답)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        log.error("500 Internal Server Error - Server error occurred: {}", ex.getMessage(), ex);

        Map<String, Object> body = new HashMap<>();
        body.put(STATUS, HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put(ERROR, "Internal Server Error");
        body.put(MESSAGE, "An unexpected error occurred on the server.");

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
