package com.example.springbootseed.common.property;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * 데이터 소스와 관련된 환경 변수를 처리하고 검증(Validate)을 수행하는 클래스
 */
@Slf4j
@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "spring.datasource")
public class DataSourceProperties {

    // 데이터베이스 URL (필수 입력 값)
    @NotBlank(message = "DATABASE_URL must not be blank")
    private String url;

    // 데이터베이스 사용자명 (필수 입력 값)
    @NotBlank(message = "DATABASE_USERNAME must not be blank")
    private String username;

    // 데이터베이스 비밀번호 (필수 입력 값)
    @NotBlank(message = "DATABASE_PASSWORD must not be blank")
    private String password;

    // 데이터베이스 드라이버 클래스 이름 (필수 입력 값)
    @NotBlank(message = "DATABASE_DRIVER must not be blank")
    private String driverClassName;

    // 기본 타임존 설정 (기본값: UTC)
    private String timezone = "UTC";

    // 글로벌 인식 식별자 사용 여부 (기본값: false)
    private boolean globallyQuotedIdentifiers = false;
}
