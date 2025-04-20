package com.example.springbootseed.common.property;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

/**
 * 데이터 소스와 관련된 환경 변수를 처리하고 검증(Validate)을 수행하는 클래스입니다.
 */
@Getter
@Validated
@ConfigurationProperties(prefix = "spring.datasource")
public class DataSourceProperties {

    // 데이터베이스 URL (필수 입력 값)
    @NotBlank(message = "DATABASE_URL must not be blank")
    private final String url;

    // 데이터베이스 사용자명 (필수 입력 값)
    @NotBlank(message = "DATABASE_USERNAME must not be blank")
    private final String username;

    // 데이터베이스 비밀번호 (필수 입력 값)
    @NotBlank(message = "DATABASE_PASSWORD must not be blank")
    private final String password;

    // 데이터베이스 드라이버 클래스 이름 (필수 입력 값)
    @NotBlank(message = "DATABASE_DRIVER must not be blank")
    private final String driverClassName;

    // 기본 타임존 설정 (기본값: UTC)
    private final String timezone;

    // 글로벌 인식 식별자 사용 여부 (기본값: false)
    private final boolean globallyQuotedIdentifiers;

    /**
     * 생성자 기반으로 프로퍼티를 바인딩합니다.
     *
     * @param url                       데이터베이스 URL
     * @param username                  사용자명
     * @param password                  비밀번호
     * @param driverClassName           드라이버 클래스 이름
     * @param timezone                  타임존 (기본값: UTC)
     * @param globallyQuotedIdentifiers 글로벌 식별자 사용 여부 (기본값: false)
     */
    @ConstructorBinding
    public DataSourceProperties(
        String url,
        String username,
        String password,
        String driverClassName,
        String timezone,
        boolean globallyQuotedIdentifiers
    ) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.driverClassName = driverClassName;
        this.timezone = (timezone != null && !timezone.isBlank()) ? timezone : "UTC";
        this.globallyQuotedIdentifiers = globallyQuotedIdentifiers;
    }
}
