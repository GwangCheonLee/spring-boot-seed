package com.example.springbootseed.common.property;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Configuration;

/**
 * {@link DataSourceProperties} 클래스의 외부 설정 값 바인딩을 검증하는 단위 테스트입니다.
 */
@DisplayName("DataSourceProperties 설정 바인딩 테스트")
class DataSourcePropertiesTest {

    /**
     * ApplicationContextRunner는 Spring 컨텍스트 없이 ConfigurationProperties를 테스트할 수 있는 도구입니다.
     */
    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
        .withUserConfiguration(TestConfig.class)
        .withPropertyValues(
            "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
            "spring.datasource.username=sa",
            "spring.datasource.password=1234",
            "spring.datasource.driver-class-name=org.h2.Driver",
            "spring.datasource.timezone=UTC",
            "spring.datasource.globally-quoted-identifiers=false"
        );

    /**
     * 설정한 프로퍼티 값들이 {@link DataSourceProperties} 객체에 정상적으로 바인딩되는지 검증합니다.
     */
    @Test
    @DisplayName("프로퍼티 값이 DataSourceProperties에 정상 주입되어야 한다")
    void dataSourcePropertiesShouldLoadCorrectly() {
        contextRunner.run(context -> {
            DataSourceProperties properties = context.getBean(DataSourceProperties.class);

            assertThat(properties.getUrl())
                .as("데이터베이스 URL 확인")
                .isEqualTo("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");

            assertThat(properties.getUsername())
                .as("데이터베이스 사용자명 확인")
                .isEqualTo("sa");

            assertThat(properties.getPassword())
                .as("데이터베이스 비밀번호 확인")
                .isEqualTo("1234");

            assertThat(properties.getDriverClassName())
                .as("드라이버 클래스 확인")
                .isEqualTo("org.h2.Driver");

            assertThat(properties.getTimezone())
                .as("타임존 기본값 확인")
                .isEqualTo("UTC");

            assertThat(properties.isGloballyQuotedIdentifiers())
                .as("글로벌 식별자 사용 여부 확인")
                .isFalse();
        });
    }

    /**
     * 테스트에 사용할 임시 구성 클래스. {@link DataSourceProperties}를 context에 등록하기 위한 설정입니다.
     */
    @Configuration
    @EnableConfigurationProperties(DataSourceProperties.class)
    static class TestConfig {
        // 별도 Bean 등록 없이 프로퍼티 클래스만 활성화
    }
}
