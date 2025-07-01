package com.example.springbootseed.common.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 설정 클래스
 */
@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final Environment environment;

    /**
     * Spring Security 설정을 적용하는 SecurityFilterChain Bean 등록
     *
     * @param httpSecurity Spring Security의 HttpSecurity 객체
     * @return SecurityFilterChain 보안 필터 체인 객체
     * @throws Exception 설정 과정에서 발생할 수 있는 예외
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        // CSRF 보호 비활성화 (REST API 환경에서는 불필요)
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        // 세션을 사용하지 않는 Stateless 정책 설정 (JWT 인증 방식 사용)
        httpSecurity.sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        httpSecurity.authorizeHttpRequests(authorizeHttpRequests -> {

            // 운영 환경이 아닌 경우 Swagger 및 Actuator 접근 허용
            if (!isProdProfile()) {
                authorizeHttpRequests.requestMatchers(
                    "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**",
                    "/swagger-resources/**", "/webjars/**",
                    "/actuator/**"
                ).permitAll();
            } else {
                authorizeHttpRequests.requestMatchers("/actuator/health").permitAll();
                authorizeHttpRequests.requestMatchers(
                    "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**",
                    "/swagger-resources/**", "/webjars/**",
                    "/actuator/**"
                ).denyAll();
            }

            // 그 외의 모든 요청은 인증 필요
            authorizeHttpRequests.anyRequest().authenticated();
        });

        // 폼 로그인 및 HTTP Basic 인증 비활성화
        httpSecurity.formLogin(AbstractHttpConfigurer::disable);
        httpSecurity.httpBasic(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }


    /**
     * AuthenticationManager Bean 등록
     *
     * @param http HttpSecurity 객체
     * @return AuthenticationManager 인증 관리자 객체
     * @throws Exception 설정 과정에서 발생할 수 있는 예외
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }

    /**
     * 현재 프로파일이 운영(production) 환경인지 확인하는 메서드
     *
     * @return 운영 환경(production) 여부 (true: 운영 환경, false: 비운영 환경)
     */
    private boolean isProdProfile() {
        return environment.acceptsProfiles(Profiles.of("prod"));
    }
}
