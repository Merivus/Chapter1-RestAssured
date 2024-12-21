package com.testcases;

import com.base.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class GetUsersTest extends BaseTest {

  @Test
  public void validateUserIdsAreNotNull() {
    getLogger().info("Test started: validateUserIdsAreNotNull");
    getTest().info("Test started: validateUserIdsAreNotNull");

    // Set Base URI
    RestAssured.baseURI = "https://gorest.co.in/public/v1";
    getLogger().debug("Base URI set to: {}", RestAssured.baseURI);
    getTest().info("Base URI set to: " + RestAssured.baseURI);

    // Send GET request
    Response response = RestAssured
        .given()
        .when()
        .get("/users")
        .then()
        .statusCode(200) // Validate HTTP status code
        .extract()
        .response();

    getLogger().info("Response received with status code: {}", response.getStatusCode());
    getTest().info("Response received with status code: " + response.getStatusCode());

    // Extract and validate IDs
    List<Integer> userIds = response.jsonPath().getList("data.id");
    long nullIdCount = userIds.stream().filter(id -> id == null).count();
    getLogger().debug("Total IDs: {}, Null IDs: {}", userIds.size(), nullIdCount);
    getTest().info("Total IDs: " + userIds.size());
    getTest().info("Null IDs: " + nullIdCount);

    Assert.assertEquals(nullIdCount, 0, "Null IDs found!");
    getLogger().info("All user IDs are valid.");
    getTest().pass("All user IDs are valid.");
  }
}