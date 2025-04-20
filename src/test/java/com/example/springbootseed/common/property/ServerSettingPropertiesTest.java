package com.example.springbootseed.common.property;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Configuration;

/**
 * {@link ServerSettingProperties} 클래스의 외부 설정 값 바인딩을 검증하는 단위 테스트입니다.
 */
@DisplayName("ServerSettingProperties 설정 바인딩 테스트")
class ServerSettingPropertiesTest {

    /**
     * ApplicationContextRunner는 Spring 컨텍스트 없이 ConfigurationProperties를 테스트할 수 있는 도구입니다.
     */
    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
        .withUserConfiguration(TestConfig.class)
        .withPropertyValues(
            "server.setting.timezone=UTC"
        );


    /**
     * 설정한 프로퍼티 값들이 {@link ServerSettingProperties} 객체에 정상적으로 바인딩되는지 검증합니다.
     */
    @Test
    @DisplayName("프로퍼티 값이 ServerSettingProperties에 정상 주입되어야 한다")
    void serverSettingPropertiesShouldLoadCorrectly() {
        contextRunner.run(context -> {
            ServerSettingProperties properties = context.getBean(ServerSettingProperties.class);

            assertThat(properties.getTimezone()).isEqualTo("UTC");
        });
    }

    /**
     * 테스트에 사용할 임시 구성 클래스. {@link ServerSettingProperties}를 context에 등록하기 위한 설정입니다.
     */
    @Configuration
    @EnableConfigurationProperties(ServerSettingProperties.class)
    static class TestConfig {
        // 빈 등록은 필요 없음. 어노테이션으로 설정 클래스 등록만 수행.
    }
}

