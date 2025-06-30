package helpers;


import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.restassured.response.Response;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import tests.BookСollectionTest;

import java.util.UUID;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.given;
import static specs.SpecsList.RequestSpec;
import static specs.SpecsList.Response200Spec;

//public class WithLoginExtension implements BeforeEachCallback {
//    public static Response authResponse;
//
//    @Override
//    public void beforeEach(ExtensionContext context) {
//        Object testInstance = context.getRequiredTestInstance();
//
//        if (testInstance instanceof BookСollectionTest) {
//
//            String authData = "{\"userName\":\"" + TestData.login + "\",\"password\":\"" + TestData.password + "\"}";
//
//            authResponse = given(RequestSpec)
//                    .body(authData)
//                    .when()
//                    .post("/Account/v1/Login")
//                    .then()
//                    .spec(Response200Spec)
//                    .extract().response();
//
//            open("/favicon.ico");
//
//            getWebDriver().manage().addCookie(new Cookie("userID", authResponse.path("userId")));
//            getWebDriver().manage().addCookie(new Cookie("expires", authResponse.path("expires")));
//            getWebDriver().manage().addCookie(new Cookie("token", authResponse.path("token")));
//        }
//    }
//}
public class WithLoginExtension implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) {
        // 1. Настройка ChromeOptions для CI
        ChromeOptions options = new ChromeOptions();
        options.addArguments(
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--remote-allow-origins=*",
                "--user-data-dir=/tmp/chrome-profile-" + UUID.randomUUID()
        );

        // 2. Инициализация драйвера
        WebDriverRunner.setWebDriver(new ChromeDriver(options));

        // 3. Авторизация через API
        Response authResponse = given()
                .spec(RequestSpec)
                .body("{\"userName\":\"" + TestData.login + "\",\"password\":\"" + TestData.password + "\"}")
                .post("/Account/v1/Login")
                .then()
                .spec(Response200Spec)
                .extract().response();

        // 4. Установка кук
        open("/favicon.ico");
        getWebDriver().manage().addCookie(new Cookie("userID", authResponse.path("userId")));
        getWebDriver().manage().addCookie(new Cookie("token", authResponse.path("token")));
    }

    @Override
    public void afterEach(ExtensionContext context) {
        // 5. Обязательное закрытие драйвера
        Selenide.closeWebDriver();
    }
}