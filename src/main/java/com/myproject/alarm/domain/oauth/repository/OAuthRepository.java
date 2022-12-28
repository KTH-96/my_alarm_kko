package com.myproject.alarm.domain.oauth.repository;

import com.myproject.alarm.domain.oauth.OAuthToken;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

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
        return store.get("accessToken");
    }

    public String findRefreshToken() {
        return store.get("refreshToken");
    }
}
