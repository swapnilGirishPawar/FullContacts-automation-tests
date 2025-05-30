package tests;

import base.BaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class StaffTests extends BaseTest {

    @Test(priority = 2)
    public void createStaff() {
        String body = """
        [
          {
            "email": "updatedstaff@maildrop.cc",
            "firstName": "John",
            "lastName": "Doe",
            "role": "admin"
          }
        ]
        """;

        Response res = given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType("application/json")
                .body(body)
                .post("/api/v2/account/" + accountId + "/add/member");

        Assert.assertEquals(res.statusCode(), 200);
        Assert.assertTrue(res.jsonPath().getBoolean("ok"));
        Assert.assertEquals(res.jsonPath().getInt("data.invitations[0].code"), 201);
    }
}
