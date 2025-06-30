package helpers;


import io.restassured.response.Response;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.Cookie;
import tests.BookСollectionTest;
import tests.LoginTest;


import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.given;
import static specs.SpecsList.RequestSpec;
import static specs.SpecsList.Response200Spec;

public class WithLoginExtension implements BeforeEachCallback {
    public static Response authResponse;

    @Override
    public void beforeEach(ExtensionContext context) {
        Object testInstance = context.getRequiredTestInstance();

        if (testInstance instanceof BookСollectionTest) {

            String authData = "{\"userName\":\"" + TestData.login + "\",\"password\":\"" + TestData.password + "\"}";

            authResponse = given(RequestSpec)
                    .body(authData)
                    .when()
                    .post("/Account/v1/Login")
                    .then()
                    .spec(Response200Spec)
                    .extract().response();

            open("/favicon.ico");

            getWebDriver().manage().addCookie(new Cookie("userID", authResponse.path("userId")));
            getWebDriver().manage().addCookie(new Cookie("expires", authResponse.path("expires")));
            getWebDriver().manage().addCookie(new Cookie("token", authResponse.path("token")));
        }
    }
}
