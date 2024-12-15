package com.example.qaframework.helpers;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Базовый хелпер для работы с rest запросами
 */
public class BaseApiHelper {

    private static RequestSpecification given() {
        RequestSpecification specification = new RequestSpecBuilder()
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();

        return RestAssured.given(specification);
    }

    private static String getAuthHeader(String username, String password) {
        return "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8));
    }

    protected Response get(String url) {
        return given()
                .when()
                .get(url)
                .then()
                .extract()
                .response();
    }

    protected Response post(String url, Object body) {
        return given()
                .contentType("application/json")
                .body(body)
                .when()
                .post(url)
                .then()
                .extract()
                .response();
    }

    protected Response put(String url, Object body, String username, String password) {
        return given()
                .contentType("application/json")
                .header("Authorization", getAuthHeader(username, password))
                .body(body)
                .when()
                .put(url)
                .then()
                .extract()
                .response();
    }

    protected Response delete(String url, String username, String password) {
        return given()
                .header("Content-Type", "application/json")
                .header("Authorization", getAuthHeader(username, password))
                .when()
                .delete(url)
                .then()
                .log().all()
                .extract()
                .response();
    }
}
