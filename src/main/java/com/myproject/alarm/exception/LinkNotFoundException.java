package com.myproject.alarm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LinkNotFoundException extends RuntimeException {

    private final ErrorMessage errorMessage;
}
