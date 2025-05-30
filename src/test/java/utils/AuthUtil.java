package utils;

import base.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static utils.CommonUtil.ReadProperties;
import static utils.CommonUtil.configCredential;

public class AuthUtil {
    public static void fetchToken() {
        Response response = given()
                .baseUri("https://fullcreative.fullauth.com")
                .basePath("/o/oauth2/v1/token")
                .contentType("application/x-www-form-urlencoded")
                .formParam("grant_type", "password")
                .formParam("username", configCredential("username"))
                .formParam("password", configCredential("password"))
                .formParam("client_id", configCredential("client_id"))
                .formParam("client_secret", configCredential("client_secret"))
                .formParam("scope", configCredential("scope"))
                .post().then().extract().response();

        String token = response.jsonPath().getString("access_token");
        String accountId = response.jsonPath().getString("acct_id");
        String userId = response.jsonPath().getString("user_id");

        base.BaseTest.accessToken = token;
        base.BaseTest.accountId = accountId;
        base.BaseTest.userId = userId;
        if(response.statusCode() == 200) {
            System.err.println("Fetched access token. Status code: " + response.statusCode());
        } else {
            System.err.println("Failed to fetch access token. Status code: " + response.statusCode());
            throw new RuntimeException("Authentication failed");
        }
    }
}
