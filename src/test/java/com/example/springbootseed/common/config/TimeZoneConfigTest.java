package com.example.springbootseed.common.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.example.springbootseed.common.property.ServerSettingProperties;
import java.util.TimeZone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TimeZoneConfigTest {

    @Mock
    private ServerSettingProperties serverSettingProperties;

    private TimeZoneConfig timeZoneConfig;

    @BeforeEach
    void setUp() {
        timeZoneConfig = new TimeZoneConfig(serverSettingProperties);
    }

    @Test
    void testSetDefaultTimeZone() {
        // given: 서버 설정 프로퍼티에 테스트용 타임존 값을 지정합니다.
        String expectedTimeZoneId = "Asia/Seoul";
        when(serverSettingProperties.getTimezone()).thenReturn(expectedTimeZoneId);

        // when: 기본 타임존 설정 메서드를 호출합니다.
        timeZoneConfig.setDefaultTimeZone();

        // then: 시스템의 기본 타임존이 올바르게 설정되었는지 검증합니다.
        TimeZone actualTimeZone = TimeZone.getDefault();
        assertEquals(expectedTimeZoneId, actualTimeZone.getID());
    }
}
