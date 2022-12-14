package com.myproject.alarm.domain.weather;

import com.myproject.alarm.exception.ErrorMessage;
import com.myproject.alarm.exception.WeatherInfoNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

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

    private void validWeatherInfo(String nowTemperature, String todayTemperature) {
        if (!StringUtils.hasText(nowTemperature) || !StringUtils.hasText(todayTemperature)) {
            log.info("not found temperature data");
            throw new WeatherInfoNotFoundException(ErrorMessage.WEATHER_INFO_NOT_FOUND);
        }
    }

    public String createWeatherInfoText() {
        StringBuilder sb = new StringBuilder();
        List<String> infoList = new ArrayList<>();
        infoList.add(nowTemperature);
        infoList.add(todayLowTemperature);
        infoList.add(todayHighTemperature);
        for (String info : infoList) {
            StringTokenizer st = new StringTokenizer(info, "\n");
            sb.append(st.nextToken()).append(": ").append(st.nextToken()).append("\n");
        }
        return sb.toString();
    }
}
