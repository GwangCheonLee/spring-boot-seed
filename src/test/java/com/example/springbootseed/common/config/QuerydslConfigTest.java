package com.example.springbootseed.common.config;

import static org.assertj.core.api.Assertions.assertThat;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

/**
 * {@link QuerydslConfig} 클래스의 JPAQueryFactory 빈 등록 여부를 검증하는 테스트입니다.
 */
@SpringBootTest
@ActiveProfiles("test")
@DisplayName("QuerydslConfig - JPAQueryFactory 등록 테스트")
class QuerydslConfigTest {

    @Autowired
    private ApplicationContext applicationContext;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Spring 컨텍스트에 {@link JPAQueryFactory} 빈이 정상 등록되어 있는지 확인합니다.
     */
    @Test
    @DisplayName("JPAQueryFactory 빈 등록 확인")
    void shouldLoadJPAQueryFactoryBeanFromContext() {
        JPAQueryFactory jpaQueryFactory = applicationContext.getBean(JPAQueryFactory.class);

        assertThat(jpaQueryFactory)
            .as("JPAQueryFactory 빈이 ApplicationContext에 등록되어 있어야 한다")
            .isNotNull();
    }

    /**
     * EntityManager를 수동 주입하여 JPAQueryFactory 인스턴스를 직접 생성할 수 있는지 확인합니다.
     */
    @Test
    @DisplayName("EntityManager로 JPAQueryFactory 직접 생성 가능 여부 확인")
    void shouldCreateJPAQueryFactoryManually() {
        assertThat(entityManager).isNotNull();

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);

        assertThat(jpaQueryFactory)
            .as("EntityManager로 JPAQueryFactory 직접 생성이 가능해야 한다")
            .isNotNull();
    }
}
