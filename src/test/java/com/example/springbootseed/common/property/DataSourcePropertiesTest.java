package com.example.springbootseed.common.property;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@EnableConfigurationProperties(DataSourceProperties.class)
class DataSourcePropertiesTest {

    @Autowired
    private DataSourceProperties dataSourceProperties;

    @Test
    void testDataSourceProperties() {
        assertThat(dataSourceProperties.getUrl()).isEqualTo(
            "jdbc:h2:mem:postgres_testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
        assertThat(dataSourceProperties.getUsername()).isEqualTo("sa");
        assertThat(dataSourceProperties.getPassword()).isEqualTo("1234");
        assertThat(dataSourceProperties.getDriverClassName()).isEqualTo("org.h2.Driver");
        assertThat(dataSourceProperties.getTimezone()).isEqualTo("UTC");
        assertThat(dataSourceProperties.isGloballyQuotedIdentifiers()).isFalse();
    }
}
