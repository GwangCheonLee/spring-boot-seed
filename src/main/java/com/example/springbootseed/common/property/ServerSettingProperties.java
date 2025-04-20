package com.example.springbootseed.common.property;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

/**
 * Server Setting 과 관련된 환경 변수를 처리하는 클래스입니다.
 */
@Getter
@Validated
@ConfigurationProperties(prefix = "server.setting")
public class ServerSettingProperties {

    private final String timezone;

    /**
     * 서버 설정 프로퍼티를 초기화합니다.
     *
     * @param timezone 서버의 기본 타임존을 설정합니다.
     */
    @ConstructorBinding
    public ServerSettingProperties(String timezone) {
        this.timezone = (timezone == null || timezone.isBlank()) ? "UTC" : timezone;
    }
}
