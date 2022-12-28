package com.myproject.alarm.service;

import static com.myproject.alarm.utils.Constant.ACCESS_TOKEN_URL;
import static com.myproject.alarm.utils.Constant.APP_TYPE_URL_ENCODED;

import com.myproject.alarm.domain.oauth.OAuthToken;
import com.myproject.alarm.domain.oauth.repository.OAuthRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class OAuthService {

    private final String client_id;

    private final String client_secrete;

    private final String redirect_url;

    private final OAuthRepository oAuthRepository;

    public OAuthService(@Value("${oauth2.kakao.client_id}")String client_id,
                        @Value("${oauth2.kakao.client_secrete}")String client_secrete,
                        @Value("${oauth2.kakao.redirect_url}")String redirect_url, OAuthRepository oAuthRepository) {
        this.client_id = client_id;
        this.client_secrete = client_secrete;
        this.redirect_url = redirect_url;
        this.oAuthRepository = oAuthRepository;
    }

    public OAuthToken getAccessToken(String code) {
        log.info("토큰 발급 시작");
        HttpHeaders header = new HttpHeaders();
        header.set("Content-Type", APP_TYPE_URL_ENCODED);
        header.set("Authorization", "Bearer " + code);

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("code", code);
        parameters.add("grant_type", "authorization_code");
        parameters.add("client_id", client_id);
        parameters.add("redirect_url", redirect_url);
        parameters.add("client_secret", client_secrete);

        HttpEntity<?> requestEntity = new HttpEntity<>(parameters, header);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response =
                restTemplate.exchange(ACCESS_TOKEN_URL, HttpMethod.POST, requestEntity, String.class);

        JSONObject json = new JSONObject(response.getBody());
        OAuthToken oAuth = OAuthToken.builder()
                .accessToken(json.get("access_token").toString())
                .refreshToken(json.get("refresh_token").toString())
                .build();

        log.info("토큰 accessToken = {}, refreshToken = {}", oAuth.getAccessToken(), oAuth.getRefreshToken());
        return oAuth;
    }

    public void saveTokens(OAuthToken oAuthTokens) {
        oAuthRepository.save(oAuthTokens);
    }

    public void updateAccessToken() {
        log.info("토큰 갱신 시작");
        HttpHeaders header = new HttpHeaders();
        header.set("Content-Type", APP_TYPE_URL_ENCODED);

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("grant_type", "refresh_token");
        parameters.add("client_id", client_id);
        parameters.add("refresh_token", oAuthRepository.findRefreshToken());
        parameters.add("client_secret", client_secrete);

        HttpEntity<?> requestEntity = new HttpEntity<>(parameters, header);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response =
                restTemplate.exchange(ACCESS_TOKEN_URL, HttpMethod.POST, requestEntity, String.class);

        JSONObject json = new JSONObject(response.getBody());
        oAuthRepository.updateAccessToken(json.get("access_token").toString());

        log.info("토큰 accessToken = {} ", json.get("access_token").toString());
    }

    public void updateRefreshToken() {
        log.info("리프레쉬 토큰 갱신 시작");
        HttpHeaders header = new HttpHeaders();
        header.set("Content-Type", APP_TYPE_URL_ENCODED);

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("grant_type", "refresh_token");
        parameters.add("client_id", client_id);
        parameters.add("refresh_token", oAuthRepository.findRefreshToken());
        parameters.add("client_secret", client_secrete);

        HttpEntity<?> requestEntity = new HttpEntity<>(parameters, header);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response =
                restTemplate.exchange(ACCESS_TOKEN_URL, HttpMethod.POST, requestEntity, String.class);

        JSONObject json = new JSONObject(response.getBody());
        oAuthRepository.updateRefreshToken(json.get("refresh_token").toString());

        log.info("토큰 refresh_token = {} ", json.get("refresh_token").toString());
    }
}
