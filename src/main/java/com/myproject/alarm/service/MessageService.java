package com.myproject.alarm.service;

import static com.myproject.alarm.utils.Constant.APP_TYPE_URL_ENCODED;
import static com.myproject.alarm.utils.Constant.MESSAGE_SEND_URL;
import static com.myproject.alarm.utils.Constant.SUCCESS;

import com.myproject.alarm.domain.message.Url;
import com.myproject.alarm.domain.message.TextMessage;
import com.myproject.alarm.domain.oauth.repository.OAuthRepository;
import com.myproject.alarm.domain.weather.WeatherInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessageService {

    private final OAuthRepository oAuthRepository;

    public void sendWeatherInfo(String accessToken, WeatherInfo weatherInfo) {
        TextMessage weatherInfoMessage = TextMessage.createWeatherInfoMessage(weatherInfo);
        log.info("날씨 메세지 보내기 시작");
        HttpHeaders header = new HttpHeaders();
        header.set("Content-Type", APP_TYPE_URL_ENCODED);
        header.set("Authorization", "Bearer " + oAuthRepository.findAccessToken());

        JSONObject link = new JSONObject();
        Url url = weatherInfoMessage.getLink();
        link.put("web_url", url.getWebUrl());
        link.put("mobile_web_url", url.getMobileUrl());

        JSONObject template = new JSONObject();
        template.put("object_type", weatherInfoMessage.getObjectType());
        template.put("text", weatherInfoMessage.getText());
        template.put("link", link);
        template.put("button_title", weatherInfoMessage.getButtonTitle());

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("template_object", template.toString());

        HttpEntity<?> requestEntity = new HttpEntity<>(parameters, header);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response =
                restTemplate.exchange(MESSAGE_SEND_URL, HttpMethod.POST, requestEntity, String.class);

        JSONObject jsonData = new JSONObject(response.getBody());
        checkSuccess(jsonData.get("result_code").toString());
    }

    public void sendTestMessage() {
        TextMessage testMessage = TextMessage.createTestMessage();
        log.info("메세지 테스트 시작");
        HttpHeaders header = new HttpHeaders();
        header.set("Content-Type", APP_TYPE_URL_ENCODED);
        header.set("Authorization", "Bearer " + oAuthRepository.findAccessToken());

        JSONObject link = new JSONObject();
        Url url = testMessage.getLink();
        link.put("web_url", url.getWebUrl());
        link.put("mobile_web_url", url.getMobileUrl());

        JSONObject template = new JSONObject();
        template.put("object_type", testMessage.getObjectType());
        template.put("text", testMessage.getText());
        template.put("link", link);
        template.put("button_title", testMessage.getButtonTitle());

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("template_object", template.toString());

        HttpEntity<?> requestEntity = new HttpEntity<>(parameters, header);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response =
                restTemplate.exchange(MESSAGE_SEND_URL, HttpMethod.POST, requestEntity, String.class);

        JSONObject jsonData = new JSONObject(response.getBody());
        checkSuccess(jsonData.get("result_code").toString());
    }
    //TODO: 예외처리

    private void checkSuccess(String result_code) {
        if (!result_code.equals(SUCCESS)) {
            log.info("메세지 보내기에 실패했습니다.");
            return;
        }
        log.info("메세지 보내기에 성공했습니다.");
    }
}
