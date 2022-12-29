package com.myproject.alarm.domain.oauth;

import com.myproject.alarm.exception.ErrorMessage;
import com.myproject.alarm.exception.GlobalException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

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

    public void validToken(String accessToken, String refreshToken) {
        if (StringUtils.hasText(accessToken) || StringUtils.hasText(refreshToken)) {
            log.info("토큰 발급 실패");
            throw new GlobalException(ErrorMessage.HAS_NOT_TOKEN);
        }
        log.info("토큰 발급 성공");
    }
}
