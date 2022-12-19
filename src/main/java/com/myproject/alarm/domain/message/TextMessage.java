package com.myproject.alarm.domain.message;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
        validTextTemplate(objectType, text, link, buttonTitle);
        this.objectType = objectType;
        this.text = text;
        this.link = link;
        this.buttonTitle = buttonTitle;
    }

    public static TextMessage createTestMessage() {
        return TextMessage.builder()
                .objectType("text")
                .text("테스트 메세지 입니다.")
                .link(Url.createTestUrl())
                .buttonTitle("Url 링크")
                .build();
    }

    private void validTextTemplate(String objectType, String text, Url link, String buttonTitle) {
        if (!objectType.equals("text") || objectType.isEmpty()) {
            log.info("objectType data fail");
            log.info("objectType = {}", objectType);
        }
        if (text.length() > 200 || text.isEmpty()) {
            log.info("text data fail");
            log.info("text = {}", text);
        }
        if (buttonTitle.isEmpty()) {
            log.info("buttonTitle data fail");
            log.info("buttonTitle = {}", buttonTitle);
        }
        if (!link.hasUrl()) {
            log.info("link data fail");
            log.info("webLink = {}, mobileLink = {}", link.getWebUrl(), link.getMobileUrl());
        }
    }
}
