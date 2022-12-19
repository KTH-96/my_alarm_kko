package com.myproject.alarm.domain.message;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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

    public static Url createTestUrl() {
        return Url.builder()
                .webUrl("https://www.naver.com/")
                .mobileUrl("https://www.naver.com/")
                .build();
    }

    //TODO: 예외처리
    private void validUrl(String webUrl, String mobileUrl) {
        if (webUrl.isEmpty() || mobileUrl.isEmpty()) {
            log.info("URL 정보가 들어가 있지 않습니다.");
            log.info("webUrl = {}", webUrl);
            log.info("mobileUrl = {}", mobileUrl);
            return;
        }
        log.info("url 정보 주입 성공");
    }

    public boolean hasUrl() {
        if (webUrl.isEmpty() || mobileUrl.isEmpty()) {
            log.info("URL 정보가 들어가 있지 않습니다.");
            log.info("webUrl = {}", webUrl);
            log.info("mobileUrl = {}", mobileUrl);
            return false;
        }
        return true;
    }
}
