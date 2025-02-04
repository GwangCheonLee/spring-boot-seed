package com.example.springbootseed.common.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Server Setting 과 관련된 환경 변수를 처리하는 클래스입니다.
 */
@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "server.setting")
public class ServerSettingProperties {

    // timezone: 기본값을 UTC로 설정
    private String timezone = "UTC";
}
