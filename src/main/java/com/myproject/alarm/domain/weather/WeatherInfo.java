package com.myproject.alarm.domain.weather;

import java.util.StringTokenizer;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WeatherInfo {

    private String nowTemperature;
    private String todayHighTemperature;
    private String todayLowTemperature;

    @Builder
    public WeatherInfo(String nowTemperature, String todayTemperature) {
        validWeatherInfo(nowTemperature, todayTemperature);
        this.nowTemperature = nowTemperature;
        divideString(todayTemperature);
    }

    private void divideString(String todayTemperature) {
        StringTokenizer st = new StringTokenizer(todayTemperature, "/");
        this.todayLowTemperature = st.nextToken();
        this.todayHighTemperature = st.nextToken();
    }

    //TODO: 예외처리
    private void validWeatherInfo(String nowTemperature, String todayTemperature) {
        if (nowTemperature.isEmpty()) {
            log.info("현재온도 없음");
        }
        if (todayTemperature.isEmpty()) {
            log.info("오늘온도 없음");
        }
    }
}
