package com.myproject.alarm.domain.oauth;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuthToken {

    private String accessToken;
    private String refreshToken;


    @Builder
    public OAuthToken(String accessToken, String refreshToken) {
        validToken(accessToken, refreshToken);
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    //TODO: 예외처리
    public void validToken(String accessToken, String refreshToken) {
        if (accessToken.isEmpty() || refreshToken.isEmpty()) {
            log.info("토큰 발급 실패");
            log.info("accessToken = {}", accessToken);
            log.info("refreshToken = {}", refreshToken);
            return;
        }
        log.info("토큰 발급 성공");
    }
}
