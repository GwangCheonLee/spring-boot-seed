package com.example.springbootseed.common.property;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@EnableConfigurationProperties(ServerSettingProperties.class)
class ServerSettingPropertiesTest {

    @Autowired
    private ServerSettingProperties serverSettingProperties;

    @Test
    void testServerSettingProperties() {
        assertThat(serverSettingProperties.getTimezone()).isEqualTo("UTC");
    }
}

