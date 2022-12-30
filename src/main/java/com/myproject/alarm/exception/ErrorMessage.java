package com.myproject.alarm.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

    //NOT FOUND
    HAS_NOT_TOKEN(NOT_FOUND, "해당 토큰을 찾을 수 없습니다."),
    ISSUANCE_NOT_TOKEN(NOT_FOUND, "토큰 발급에 실패 했습니다."),
    LINK_NOT_FOUND(NOT_FOUND, "해당 URL 정보를 찾을 수 없습니다."),

    WEATHER_INFO_NOT_FOUND(NOT_FOUND, "날씨 정보를 찾을 수 없습니다"),

    //BAD REQUEST
    MISS_TEMPLATE_VALUE(BAD_REQUEST, "해당 템플릿 형식에 맞지 않습니다."),
    HAS_NOT_EXPIRED(BAD_REQUEST, "리프레쉬 토큰 기간이 만료 되지 않았습니다."),
    MESSAGE_NOT_SEND(BAD_REQUEST, "메세지 보내기에 실패했습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
