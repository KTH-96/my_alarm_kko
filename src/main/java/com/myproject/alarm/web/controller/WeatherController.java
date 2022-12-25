package com.myproject.alarm.web.controller;

import com.myproject.alarm.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping("/weather")
    public void weather() {
        weatherService.startProcess();
    }
}
