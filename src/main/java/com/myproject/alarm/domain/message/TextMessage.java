package com.myproject.alarm.domain.message;

import com.myproject.alarm.domain.weather.WeatherInfo;
import com.myproject.alarm.exception.ErrorMessage;
import com.myproject.alarm.exception.MessageTemplateException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

@Slf4j
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TextMessage {

    private String objectType;
    private String text;
    private Url link;
    private String buttonTitle;

    @Builder
    public TextMessage(String objectType, String text, Url link, String buttonTitle) {
        validTextMessageTemplate(objectType, text, link, buttonTitle);
        this.objectType = objectType;
        this.text = text;
        this.link = link;
        this.buttonTitle = buttonTitle;
    }

    public static TextMessage createWeatherInfoMessage(WeatherInfo weatherInfo) {
        return TextMessage.builder()
                .objectType("text")
                .text(weatherInfo.createWeatherInfoText())
                .link(Url.createWeatherUrl())
                .buttonTitle("네이버 날씨")
                .build();
    }

    private void validTextMessageTemplate(String objectType, String text, Url link, String buttonTitle) {
        if (!objectType.equals("text") || !StringUtils.hasText(objectType)) {
            templateMessageException("objectType", objectType);
        }
        if (StringUtils.hasText(text)) {
            templateMessageException("text", text);
        }
        if (text.length() > 200) {
            templateMessageException("text", "text too Long");
        }
        if (!link.hasUrl()) {
            templateMessageException("link", link.toString());
        }
        if (!StringUtils.hasText(buttonTitle)) {
            templateMessageException("buttonTitle", buttonTitle);
        }
    }

    private void templateMessageException(String type, String value) {
        log.info("{} template error value = {}", type, value);
        throw new MessageTemplateException(ErrorMessage.MISS_TEMPLATE_VALUE);
    }
}
