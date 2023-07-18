package com.iscram.reactive.resources;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.emptyString;


@QuarkusTest
@TestHTTPEndpoint(FruitResource.class)
public class FruitResourceTest {

    @Test
    public void testListAllFruits() {
        //List all, should have all 3 fruits the database has initially:
        Response response = given()
                .when()
                .get()
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().response();
        assertThat(response.jsonPath().getList("name")).containsExactlyInAnyOrder("Cherry", "Apple", "Banana");

        // Update Cherry to Pineapple
        given()
                .when()
                .body("{\"name\" : \"Pineapple\"}")
                .contentType("application/json")
                .put("/1")
                .then()
                .statusCode(200)
                .body(
                        containsString("\"id\":"),
                        containsString("\"name\":\"Pineapple\""));

        //List all, Pineapple should've replaced Cherry:
        response = given()
                .when()
                .get()
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().response();
        assertThat(response.jsonPath().getList("name"))
                .containsExactlyInAnyOrder("Pineapple", "Apple", "Banana");

        //Delete Pineapple:
        given()
                .when()
                .delete("1")
                .then()
                .statusCode(204);

        response = given()
                .when()
                .get()
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().response();
        assertThat(response.jsonPath().getList("name"))
                .containsExactlyInAnyOrder("Apple", "Banana");

        //Create the Pear:
        given()
                .when()
                .body("{\"name\" : \"Pear\"}")
                .contentType("application/json")
                .post()
                .then()
                .statusCode(201)
                .body(
                        containsString("\"id\":"),
                        containsString("\"name\":\"Pear\""));

        //List all, Pineapple should be still missing now:
        response = given()
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract().response();
        assertThat(response.jsonPath().getList("name"))
                .containsExactlyInAnyOrder("Pear", "Apple", "Banana");
    }

    @Test
    public void testEntityNotFoundForDelete() {
        given()
                .when()
                .delete("/9236")
                .then()
                .statusCode(404)
                .body(emptyString());
    }

    @Test
    public void testEntityNotFoundForUpdate() {
        given()
                .when()
                .body("{\"name\" : \"Watermelon\"}")
                .contentType("application/json")
                .put("/32432")
                .then()
                .statusCode(404)
                .body(emptyString());
    }
}