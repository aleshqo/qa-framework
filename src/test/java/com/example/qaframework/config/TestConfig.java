package com.example.qaframework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan(basePackages = "com.example.qaframework")
public class TestConfig {

    @Value("${base.url}")
    private String baseUrl;
    @Value("${base.ws.uri}")
    private String baseWsUri;

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getBaseWsUri() {
        return baseWsUri;
    }
}
