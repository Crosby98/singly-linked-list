package com.crosby98.singly_linked_list.controller;

import com.crosby98.singly_linked_list.AbstractIntegrationTest;
import com.crosby98.singly_linked_list.dto.InsertionNodeRequest;
import com.crosby98.singly_linked_list.dto.ObjectNodeRequest;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

class LinkedListControllerIntegrationTest extends AbstractIntegrationTest {


    private static final String PUBLIC_HOST = "public/list";

    @Autowired
    public LinkedListControllerIntegrationTest() {
    }

    private final Random random = new Random();

    @Test
    void testConcurrentOperations() throws Exception {
        // if everything is ok assumes that there is no deadlock
        ExecutorService executorService = Executors.newFixedThreadPool(8);

        for (int i = 0; i < 1000; i++) {
            executorService.submit(getRandomOperation());
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        Integer response = given()
                .and().headers(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .when().get(PUBLIC_HOST + "/size")
                .then()
                .assertThat().statusCode(HttpStatus.OK.value()).extract().as(new TypeRef<>() {
                });

        assertThat(response).isNotNull();
        assertThat(response).isGreaterThan(0);
    }

    private Runnable getRandomOperation() {
        Runnable operation = null;
        int rand = random.nextInt(3);
        switch (rand) {
            case 0 -> operation = this::performPushOperation;
            case 1 -> operation = this::performPopOperation;
            case 2 -> operation = this::performInsertAfterOperation;
        }
        return operation;
    }

    private void performPushOperation() {
        ObjectNodeRequest body = new ObjectNodeRequest(random.nextInt(100));

        given()
                .and().headers(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post(PUBLIC_HOST + "/push")
                .then()
                .assertThat().statusCode(HttpStatus.OK.value());
    }

    private void performPopOperation() {
        given()
                .and().headers(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .when().delete(PUBLIC_HOST + "/pop")
                .then()
                .assertThat().statusCode(HttpStatus.OK.value());
    }

    private void performInsertAfterOperation() {
        InsertionNodeRequest body = new InsertionNodeRequest(random.nextInt(100), random.nextInt(100));

        given()
                .and().headers(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post(PUBLIC_HOST + "/insertAfter")
                .then()
                .assertThat().statusCode(HttpStatus.OK.value());
    }
}