package com.testcases;

import com.base.BaseTest;
import com.utils.JsonReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PostUserTest extends BaseTest {

  @Test
  public void createUserFromJson() throws IOException {
    getLogger().info("Test started: createUserFromJson");
    getTest().info("Test started: createUserFromJson");

    // Set Base URI
    RestAssured.baseURI = "https://gorest.co.in/public/v1";
    getLogger().debug("Base URI set to: {}", RestAssured.baseURI);
    getTest().info("Base URI set to: " + RestAssured.baseURI);

    // Read JSON data
    List<Map<String, String>> users = JsonReader.readJson("src/main/resources/user_data.json");
    getLogger().debug("Loaded users: {}", users);
    getTest().info("Loaded users from JSON: " + users);

    for (Map<String, String> user : users) {
      // Generate unique email
      String uniqueEmail = user.get("name").toLowerCase().replace(" ", "") + System.currentTimeMillis() + "@example.com";
      user.put("email", uniqueEmail);

      getLogger().info("Sending POST request for user: {}", user);
      getTest().info("Sending POST request for user: " + user);

      // Send POST request
      Response response = RestAssured
          .given()
          .contentType(ContentType.JSON)
          .header("Authorization", "Bearer 1db9c9b6c959682be7c96f74ca532c3cb0bd331f46b86a92602f8d319481b6f5")
          .body(user)
          .when()
          .post("/users")
          .then()
          .extract()
          .response();

      getLogger().info("Response received with status code: {}", response.statusCode());
      getTest().info("Response received with status code: " + response.statusCode());

      if (response.statusCode() != 201) {
        getLogger().error("Unexpected status code: {}", response.statusCode());
        getTest().fail("Unexpected status code: " + response.statusCode());
        getLogger().info("Response Body: {}", response.asString());
        getTest().info("Response Body: " + response.asString());
        continue; // Proceed to the next user
      }

      // Validate response body
      Assert.assertEquals(response.jsonPath().getString("data.name"), user.get("name"), "Name mismatch!");
      Assert.assertEquals(response.jsonPath().getString("data.gender"), user.get("gender"), "Gender mismatch!");
      Assert.assertEquals(response.jsonPath().getString("data.email"), user.get("email"), "Email mismatch!");
      Assert.assertEquals(response.jsonPath().getString("data.status"), user.get("status"), "Status mismatch!");

      getLogger().info("User created successfully: {}", user.get("email"));
      getTest().pass("User created successfully: " + user.get("email"));
    }

    getLogger().info("Test finished: createUserFromJson");
    getTest().pass("Test finished: createUserFromJson");
  }
}
