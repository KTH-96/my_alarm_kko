package com.myproject.alarm.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public class ErrorResponse {

    private String error;
    private String message;
    private String code;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createTime;

    @Builder
    public ErrorResponse(String error, String message, String code, LocalDateTime createTime) {
        this.error = error;
        this.message = message;
        this.code = code;
        this.createTime = createTime;
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorMessage message) {
        return ResponseEntity
                .status(message.getHttpStatus())
                .body(ErrorResponse.builder()
                        .error(message.getHttpStatus().name())
                        .message(message.getMessage())
                        .code(message.name())
                        .createTime(LocalDateTime.now())
                        .build()
                );
    }
}
