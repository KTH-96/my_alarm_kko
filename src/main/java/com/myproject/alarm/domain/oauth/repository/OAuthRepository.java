package com.myproject.alarm.domain.oauth.repository;

import com.myproject.alarm.domain.oauth.OAuthToken;
import com.myproject.alarm.exception.ErrorMessage;
import com.myproject.alarm.exception.TokenNotFoundException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Slf4j
@Repository
public class OAuthRepository {

    private static Map<String, String> store = new HashMap<>();

    public void save(OAuthToken oAuth) {
        store.put("accessToken", oAuth.getAccessToken());
        store.put("refreshToken", oAuth.getRefreshToken());
    }

    public void updateAccessToken(String accessToken) {
        store.put("accessToken", accessToken);
    }

    public void updateRefreshToken(String refreshToken) {
        store.put("refreshToken", refreshToken);
    }

    public String findAccessToken() {
        String token = store.getOrDefault("accessToken", "");
        if (!StringUtils.hasText(token)) {
            findTokenException("accessToken");
        }
        return token;
    }

    public String findRefreshToken() {
        String token = store.getOrDefault("refreshToken", "");
        if (!StringUtils.hasText(token)) {
            findTokenException("refreshToken");
        }
        return token;
    }

    public static void findTokenException(String type) {
        log.info("{} not found error", type);
        throw new TokenNotFoundException(ErrorMessage.HAS_NOT_TOKEN);
    }
}
