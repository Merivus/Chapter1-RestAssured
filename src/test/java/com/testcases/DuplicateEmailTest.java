package com.testcases;

import com.base.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class DuplicateEmailTest extends BaseTest {

  @Test
  public void validateDuplicateEmailNotAllowed() {
    getLogger().info("Test started: validateDuplicateEmailNotAllowed");
    getTest().info("Test started: validateDuplicateEmailNotAllowed");

    // Set Base URI
    RestAssured.baseURI = "https://gorest.co.in/public/v1";
    getLogger().debug("Base URI set to: {}", RestAssured.baseURI);
    getTest().info("Base URI set to: " + RestAssured.baseURI);

    // Prepare JSON body
    Map<String, String> requestBody = new HashMap<>();
    requestBody.put("name", "Test User");
    requestBody.put("gender", "male");
    String uniqueEmail = "test" + System.currentTimeMillis() + "@example.com";
    requestBody.put("email", uniqueEmail);
    requestBody.put("status", "active");

    getLogger().debug("Request Body: {}", requestBody);
    getTest().info("Request Body prepared: " + requestBody);

    // Send initial POST request
    Response firstResponse = RestAssured
        .given()
        .contentType(ContentType.JSON)
        .header("Authorization", "Bearer 1db9c9b6c959682be7c96f74ca532c3cb0bd331f46b86a92602f8d319481b6f5")
        .body(requestBody)
        .when()
        .post("/users")
        .then()
        .statusCode(201)
        .extract()
        .response();

    getLogger().info("First user created with status code: {}", firstResponse.getStatusCode());
    getTest().info("First user created with status code: " + firstResponse.getStatusCode());

    // Send duplicate POST request
    Response duplicateResponse = RestAssured
        .given()
        .contentType(ContentType.JSON)
        .header("Authorization", "Bearer 1db9c9b6c959682be7c96f74ca532c3cb0bd331f46b86a92602f8d319481b6f5")
        .body(requestBody)
        .when()
        .post("/users")
        .then()
        .statusCode(422)
        .extract()
        .response();

    getLogger().info("Duplicate request responded with status code: {}", duplicateResponse.getStatusCode());
    getTest().info("Duplicate request responded with status code: " + duplicateResponse.getStatusCode());

    String errorMessage = duplicateResponse.jsonPath().getString("data[0].message");
    getLogger().info("Error message received: {}", errorMessage);
    getTest().info("Error message received: " + errorMessage);

    Assert.assertTrue(errorMessage.contains("already been taken"), "Unexpected error message!");
    getLogger().info("Duplicate email validation passed.");
    getTest().pass("Duplicate email validation passed.");
  }
}
