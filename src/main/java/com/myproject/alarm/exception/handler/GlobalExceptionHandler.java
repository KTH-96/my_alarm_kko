package com.myproject.alarm.exception.handler;

import com.myproject.alarm.exception.ErrorResponse;
import com.myproject.alarm.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {GlobalException.class})
    public ResponseEntity<ErrorResponse> handler(GlobalException e) {
        log.error("GlobalException.ErrorMessage = {}", e.getErrorMessage());
        return ErrorResponse.toResponseEntity(e.getErrorMessage());
    }
}
