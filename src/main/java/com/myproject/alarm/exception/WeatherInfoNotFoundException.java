package com.myproject.alarm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WeatherInfoNotFoundException extends RuntimeException {

    private final ErrorMessage errorMessage;
}
