package tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class LoginTest {
    @Test
    void testDemoQaConnection() {
        given()
                .baseUri("https://demoqa.com")
                .when()
                .get("/Account/v1/Login")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }
}
