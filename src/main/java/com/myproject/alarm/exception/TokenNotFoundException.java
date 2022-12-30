package com.myproject.alarm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenNotFoundException extends RuntimeException {

    private final ErrorMessage errorMessage;
}
