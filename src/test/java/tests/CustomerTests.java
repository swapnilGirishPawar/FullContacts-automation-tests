package tests;

import base.BaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static utils.CommonUtil.randomEmail;
import static utils.CommonUtil.randomFullName;

public class CustomerTests extends BaseTest {
    String fullName = randomFullName();
    String firstName = fullName.split(" ")[0]; // optional
    String lastName = fullName.contains(" ") ? fullName.substring(fullName.indexOf(" ") + 1) : "";
    String email = randomEmail();

    @Test(priority = 1, enabled = true)
    public void createNormalCustomer() {
        String body =String.format( """
        {
          "isVerified": true,
          "verifiedSource": "client",
          "fullName": "%s",
          "firstName": "%s",
          "lastName": "%s",
          "linkedContactMethods": [
            {
              "type": "email",
              "value": "%s",
              "deleted": false,
              "primary": true
            }
          ]
        }
        """, fullName, firstName, lastName, email);

        Response res = given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Account-Id", accountId)
                .contentType("application/json")
                .body(body)
                .post("https://contacts.anywhere.co/services/data/v3.0/resources/Customer?apikey=" + accountId);
        System.out.println("Response: " + res.asString());
        Assert.assertEquals(res.statusCode(), 200);
        Assert.assertTrue(res.jsonPath().getBoolean("success"));
    }

    @Test(priority = 2, enabled = false)
    public void createCustomerWithoutFullName() {
        String body = String.format("""
    {
      "isVerified": true,
      "verifiedSource": "client",
      "firstName": "%s",
      "lastName": "",
      "linkedContactMethods": [
        {
          "type": "email",
          "value": "john.doe@example.com",
          "deleted": false,
          "primary": true
        }
      ]
    }
    """, firstName);

        Response res = given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Account-Id", accountId)
                .contentType("application/json")
                .body(body)
                .post("https://contacts.anywhere.co/services/data/v3.0/resources/Customer?apikey=" + accountId);
        System.out.println("Response: " + res.asString());
        Assert.assertEquals(res.statusCode(), 200);
        Assert.assertTrue(res.jsonPath().getBoolean("success"));
    }

    @Test(priority = 3, enabled = true)
    public void createCustomerWithInvalidEmailFormat() {

        String body = String.format("""
    {
      "isVerified": true,
      "verifiedSource": "client",
      "fullName": "%s",
      "firstName": "%s",
      "lastName": "%s",
      "linkedContactMethods": [
        {
          "type": "email",
          "value": "Wrong@invalid",
          "deleted": false,
          "primary": true
        }
      ]
    }
    """, fullName, firstName, lastName);

        Response res = given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Account-Id", accountId)
                .contentType("application/json")
                .body(body)
                .post("https://contacts.anywhere.co/services/data/v3.0/resources/Customer?apikey=" + accountId);
        System.out.println("Response: " + res.asString());
        String actualMessage = res.jsonPath().getString("message");
        Assert.assertNotEquals(res.statusCode(), 200);
        Assert.assertFalse(res.jsonPath().getBoolean("success"));
        Assert.assertEquals(actualMessage, "Invalid email address", "The error message did not match.");
    }

    @Test(priority = 4, enabled = false)
    public void createCustomerWithEmptyRequestBody() {
        String body = "{}";

        Response res = given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Account-Id", accountId)
                .contentType("application/json")
                .body(body)
                .post("https://contacts.anywhere.co/services/data/v3.0/resources/Customer?apikey=" + accountId);
        System.out.println("Response: " + res.asString());
        Assert.assertNotEquals(res.statusCode(), 200);
        Assert.assertFalse(res.jsonPath().getBoolean("success"));
    }

    @Test(priority = 5, enabled = false)
    public void createCustomerWithInvalidFieldType() {
        String body = String.format("""
    {
      "isVerified": true,
      "verifiedSource": true, // Should be a string
      "fullName": "%s",
      "firstName": "%s",
      "lastName": "%s",
      "linkedContactMethods": [
        {
          "type": "email",
          "value": "%s",
          "deleted": false,
          "primary": true
        }
      ]
    }
    """, fullName, firstName, lastName, email);

        Response res = given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Account-Id", accountId)
                .contentType("application/json")
                .body(body)
                .post("https://contacts.anywhere.co/services/data/v3.0/resources/Customer?apikey=" + accountId);
        System.out.println("Response: " + res.asString());
        Assert.assertNotEquals(res.statusCode(), 200);
        Assert.assertFalse(res.jsonPath().getBoolean("success"));
    }
}
