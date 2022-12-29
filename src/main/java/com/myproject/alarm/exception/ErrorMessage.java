package com.myproject.alarm.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorMessage {
    HAS_NOT_TOKEN(NOT_FOUND, "해당 토큰을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
