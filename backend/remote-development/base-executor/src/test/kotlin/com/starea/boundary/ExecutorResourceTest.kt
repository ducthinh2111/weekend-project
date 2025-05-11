package com.starea.boundary

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test

@QuarkusTest
class ExecutorResourceTest {

    @Test
    fun testExecutorEndpoint() {
        given()
            .contentType(ContentType.TEXT)
            .`when`()
            .post("/execute")
            .then()
            .statusCode(204)
    }

}