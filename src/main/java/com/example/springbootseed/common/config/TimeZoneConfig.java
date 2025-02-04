package com.example.springbootseed.common.config;

import com.example.springbootseed.common.property.ServerSettingProperties;
import jakarta.annotation.PostConstruct;
import java.util.TimeZone;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 애플리케이션의 기본 TimeZone을 설정하는 클래스입니다.
 * <p>
 * {@link ServerSettingProperties}에서 설정된 타임존을 읽어와 애플리케이션 전역 기본 타임존을 설정합니다.
 * </p>
 * <p>
 * 설정된 타임존에 따라 LocalDateTime의 동작 변화를 확인할 수 있습니다.
 * </p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
class TimeZoneConfig {

    private final ServerSettingProperties serverSettingProperties;

    /**
     * 애플리케이션의 기본 TimeZone을 설정합니다.
     * <p>
     * 애플리케이션 시작 시 {@code @PostConstruct}로 호출되며, {@code ServerSettingProperties}에 정의된 TimeZone을 기준으로
     * 시스템 전역 타임존을 설정합니다.
     * </p>
     */
    @PostConstruct
    public void setDefaultTimeZone() {
        // 서버 설정 프로퍼티에서 타임존 가져오기
        String timezone = serverSettingProperties.getTimezone();
        TimeZone.setDefault(TimeZone.getTimeZone(timezone));
        log.info("Default TimeZone set to: {}", timezone);
    }
}
