package testingCheck;

import com.sun.source.tree.AssertTree;
import models.Products;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class APITests {

    // GET call
    @Test(priority = 1)
    public void getWithoutQueryParam() {
        System.out.println("API Test 1");
        String endpoint = "https://jsonplaceholder.typicode.com/posts/1";
        var res = given()
                .when().get(endpoint).then().log().body()
                .assertThat().statusCode(200)
                .body("userId", equalTo(1))
                .body("id", equalTo(1))
                .body("title", notNullValue())
                .body("body", notNullValue());
    }

    @Test(priority = 2)
    public void getWithQuesryParam() {
        System.out.println("API Test 2");
        String endpoint = "https://jsonplaceholder.typicode.com/posts/2";
        var res = given().queryParam("id", 2)
                .when().get(endpoint).then().log().headers().assertThat().statusCode(200).header("content-type", "application/json; charset=utf-8");
        res.log().body();
    }

    // POST call
    @Test
    public void postMethod() {
        System.out.println("API Test 3");
        String endpoint = "https://jsonplaceholder.typicode.com/posts";
        String body = "{\n" +
                "  \"title\": \"foo\",\n" +
                "  \"body\": \"bar\",\n" +
                "  \"userId\": 1\n" +
                "}";
        var res = given().contentType(JSON).body(body).post(endpoint).then();
        System.out.println("Response code: " + res.extract().statusCode());
        res.log().body();
    }

    @Test
    public void putMethod() {
        String endpoint = "https://jsonplaceholder.typicode.com/posts/1";
        String body = "{ \"id\": 1, \"title\": \"updated title\", \"body\": \"updated body\", \"userId\": 1 }";
        var res = given()
                .contentType(JSON)
                .body(body)
                .when()
                .put(endpoint).then();
        res.log().body();
        System.out.println("Response code: " + res.extract().statusCode());
    }

    @Test
    public void deleteMethod() {
        String endpoint = "https://jsonplaceholder.typicode.com/posts/1";
        var res = given()
                .when()
                .delete(endpoint).then();
        res.log().body();
        System.out.println("Response code: " + res.extract().statusCode());
    }

    @Test
    public void createSerialise() {
        String endpoint = "https://jsonplaceholder.typicode.com/posts";
        Products products = new Products("foo", "bar", 1, 1);
        var res = given()
                .body(products)
                .when()
                .post(endpoint).then();
        res.log().body();
        System.out.println("Response code: " + res.extract().statusCode());
    }
    @Test
    public void createSerialiseAndGetDeserializeProducts() {
        String endpoint = "https://jsonplaceholder.typicode.com/posts";
        Products expProducts = new Products(
                "foo",
                "bar",
                1,
                1
        );
        Products products = new Products("foo", "bar", 1, 1);
        Products actualProducts =
                given()
                .body(products)
                .when()
                .post(endpoint).as(Products.class);
        assertThat(actualProducts, samePropertyValuesAs(expProducts));
    }
}
