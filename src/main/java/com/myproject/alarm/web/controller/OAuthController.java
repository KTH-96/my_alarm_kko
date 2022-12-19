package com.myproject.alarm.web.controller;

import com.myproject.alarm.domain.oauth.OAuthToken;
import com.myproject.alarm.service.MessageService;
import com.myproject.alarm.service.OAuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
public class OAuthController {
    private final OAuthService oAuthService;
    private final MessageService messageService;

    @GetMapping("/oauth/kakao")
    public void getKakaoToken(@RequestParam("code") String code) {
        OAuthToken oAuthTokens = oAuthService.getAccessToken(code);
        oAuthService.saveTokens(oAuthTokens);
        messageService.sendTestMessage();
    }
}
