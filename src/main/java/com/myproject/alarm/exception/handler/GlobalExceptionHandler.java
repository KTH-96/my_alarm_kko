package com.myproject.alarm.exception.handler;

import com.myproject.alarm.exception.ErrorResponse;
import com.myproject.alarm.exception.IssuanceTokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {IssuanceTokenException.class})
    public ResponseEntity<ErrorResponse> handler(IssuanceTokenException e) {
        log.info("IssuanceTokenException.ErrorMessage = {}", e.getErrorMessage());
        return ErrorResponse.toResponseEntity(e.getErrorMessage());
    }
}
