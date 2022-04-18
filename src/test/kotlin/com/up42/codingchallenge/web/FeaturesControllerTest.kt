package com.up42.codingchallenge.web

import io.restassured.RestAssured
import io.restassured.RestAssured.given
import org.hamcrest.Matchers.anything
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FeaturesControllerTest {
    @LocalServerPort
    var port: Int = 0

    @BeforeAll
    fun beforeAll() {
        RestAssured.port = port
    }

    @Test
    fun `should get all features`() {
        given()
            .get("/features")
            .then()
            .statusCode(200)
            .body(anything())
    }
    @Test
    fun `should get feature's quicklook`() {
        given()
            .get("/features/aeaa71d6-c549-4620-99ce-f8cae750b8d5/quicklook")
            .then()
            .statusCode(200)
            .contentType("image/png")
            .body(anything())
    }
    @Test
    fun `should get error response for wrong id`() {
        given()
            .get("/features/aeaa71d6-c549-4620-99ce-f8cae750b8d0/quicklook")
            .then()
            .statusCode(404)
            .body(anything())
    }
    @Test
    fun `should get error response for invalid id`() {
        given()
            .get("/features/aeaa71d6-c549-4620-99ce-f8cae750b8d50/quicklook")
            .then()
            .statusCode(500)
            .body(anything())
    }
}
