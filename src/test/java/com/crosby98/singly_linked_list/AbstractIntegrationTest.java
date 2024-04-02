package com.crosby98.singly_linked_list;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {SinglyLinkedListApplication.class})
public abstract class AbstractIntegrationTest {

    @LocalServerPort
    protected int serverPort;

    @Value("${server.servlet.context-path}")
    protected String serverContextPath;

    @BeforeEach
    public void setUp() {
        RestAssured.port = serverPort;
        RestAssured.basePath = serverContextPath;
    }
}