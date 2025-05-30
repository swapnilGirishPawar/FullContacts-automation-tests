package base;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import utils.AuthUtil;

public class BaseTest {
    public static String accessToken;
    public static String accountId;
    public static String userId;

    @BeforeClass
    public void baseSetup() {
        RestAssured.baseURI = "https://api.anywhereworks.com";
        AuthUtil.fetchToken();
    }
}
