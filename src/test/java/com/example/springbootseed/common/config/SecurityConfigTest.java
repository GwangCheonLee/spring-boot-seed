package com.example.springbootseed.common.config;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SecurityConfig의 동작을 검증하기 위한 테스트 클래스
 */
@SpringBootTest(properties = {
    "springdoc.api-docs.enabled=false",
    "springdoc.swagger-ui.enabled=false"
})
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Import(SecurityConfigTest.TestController.class)
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * 공개 엔드포인트에 대해 인증 없이 접근이 가능한지 테스트
     */
    @Test
    void testPublicEndpoints() throws Exception {
        // 루트 경로 접근 테스트
        mockMvc.perform(get("/"))
            .andExpect(status().isOk());

        // Swagger UI 접근 테스트

    }

    /**
     * CSRF 보호가 비활성화되어 POST 요청 시 CSRF 토큰 없이도 정상 응답을 받는지 테스트
     */
    @Test
    void testPostWithoutCsrf() throws Exception {
        mockMvc.perform(post("/"))
            .andExpect(status().isOk());
    }

    /**
     * 인증이 필요한 엔드포인트에 대해 인증 없이 접근하면 403 Forbidden 응답이 반환되는지 테스트
     */
    @Test
    void testSecuredEndpointWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/secured"))
            .andExpect(status().isForbidden());
    }

    /**
     * 테스트 환경에서 사용될 더미 컨트롤러 정의
     */
    @RestController
    public static class TestController {

        // 모든 사용자에게 허용되는 엔드포인트
        @GetMapping("/")
        public ResponseEntity<String> home() {
            return ResponseEntity.ok("home");
        }

        // CSRF 보호가 비활성화된 경우 POST 요청도 허용됨
        @PostMapping("/")
        public ResponseEntity<String> postHome() {
            return ResponseEntity.ok("postHome");
        }

        // 인증이 필요한 엔드포인트 (테스트 시 인증 없이 접근하면 403 반환)
        @GetMapping("/secured")
        public ResponseEntity<String> secured() {
            return ResponseEntity.ok("secured");
        }

        // API 문서 관련 엔드포인트 (허용됨)
        @GetMapping("/v3/api-docs")
        public ResponseEntity<String> apiDocs() {
            return ResponseEntity.ok("api-docs");
        }

        // Actuator 관련 엔드포인트 (허용됨)
        @GetMapping("/actuator/health")
        public ResponseEntity<String> actuatorHealth() {
            return ResponseEntity.ok("healthy");
        }
    }


}
