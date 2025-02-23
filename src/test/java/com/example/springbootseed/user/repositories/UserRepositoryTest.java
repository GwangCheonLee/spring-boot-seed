package com.example.springbootseed.user.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.springbootseed.user.entities.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("사용자 저장 및 이메일로 조회 테스트")
    void testSaveAndFindByEmail() {
        // 준비: 테스트용 User 엔티티 생성
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setName("테스트 사용자");
        userRepository.save(user);

        // 실행: 저장된 사용자 이메일로 조회
        Optional<User> retrievedUser = userRepository.findByEmail("test@example.com");

        // 검증: 조회된 사용자 정보가 존재하고, 이메일이 일치하는지 확인
        assertThat(retrievedUser).isPresent();
        assertThat(retrievedUser.get().getEmail()).isEqualTo("test@example.com");
    }

    @TestConfiguration
    static class QuerydslConfig {

        @PersistenceContext
        private EntityManager entityManager;

        @Bean
        public JPAQueryFactory jpaQueryFactory() {
            return new JPAQueryFactory(entityManager);
        }
    }
}
