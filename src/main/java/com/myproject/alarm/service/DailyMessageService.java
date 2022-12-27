package com.myproject.alarm.service;

import com.myproject.alarm.domain.oauth.repository.OAuthRepository;
import com.myproject.alarm.domain.weather.WeatherInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class DailyMessageService {

    private final OAuthService oAuthService;
    private final OAuthRepository oAuthRepository;
    private final MessageService messageService;
    private final WeatherService weatherService;


    @Scheduled(cron = "0 0 8 * * ?")
    public void sendDailyTemperature() {
        oAuthService.updateAccessToken();
        String accessToken = oAuthRepository.findAccessToken();
        WeatherInfo weatherInfo = weatherService.crawlingWeatherData();
        messageService.sendWeatherInfo(accessToken, weatherInfo);
    }
}
