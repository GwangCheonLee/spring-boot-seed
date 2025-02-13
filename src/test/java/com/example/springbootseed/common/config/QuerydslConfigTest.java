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

@SpringBootTest
@ActiveProfiles("test")
class QuerydslConfigTest {

    @Autowired
    private ApplicationContext applicationContext;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @DisplayName("JPAQueryFactory 빈이 정상적으로 로드되었는지 확인")
    void jpaQueryFactoryBeanIsLoaded() {
        JPAQueryFactory jpaQueryFactory = applicationContext.getBean(JPAQueryFactory.class);
        assertThat(jpaQueryFactory).isNotNull();
    }

    @Test
    @DisplayName("JPAQueryFactory를 이용하여 간단한 쿼리를 실행할 수 있는지 확인")
    void jpaQueryFactoryCanExecuteQuery() {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        assertThat(jpaQueryFactory).isNotNull();
    }
}
