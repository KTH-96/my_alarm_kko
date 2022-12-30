package com.myproject.alarm.domain.message;

import static com.myproject.alarm.utils.Constant.WEATHER_DAEJEON_URL;

import com.myproject.alarm.exception.ErrorMessage;
import com.myproject.alarm.exception.LinkNotFoundException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

@Slf4j
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Url {

    private String webUrl;
    private String mobileUrl;

    @Builder
    public Url(String webUrl, String mobileUrl) {
        validUrl(webUrl, mobileUrl);
        this.webUrl = webUrl;
        this.mobileUrl = mobileUrl;
    }

    public static Url createWeatherUrl() {
        return Url.builder()
                .webUrl(WEATHER_DAEJEON_URL)
                .mobileUrl(WEATHER_DAEJEON_URL)
                .build();
    }

    //TODO: 예외처리
    private void validUrl(String webUrl, String mobileUrl) {
        if (!StringUtils.hasText(webUrl) || !StringUtils.hasText(mobileUrl)) {
            log.info("Link data not found");
            throw new LinkNotFoundException(ErrorMessage.LINK_NOT_FOUND);
        }
        log.info("url 정보 주입 성공");
    }

    public boolean hasUrl() {
        if (!StringUtils.hasText(webUrl) || !StringUtils.hasText(mobileUrl)) {
            log.info("Link data not found");
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "webUrl= '" + webUrl + '\'' +
                ", mobileUrl= '" + mobileUrl + '\'';
    }
}
