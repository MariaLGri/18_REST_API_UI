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
//public class WithLoginExtension implements BeforeEachCallback, AfterEachCallback {
//    private static final ThreadLocal<AuthData> AUTH_DATA = new ThreadLocal<>();
//
//    public static AuthData getAuthData() {
//        if (AUTH_DATA.get() == null) {
//            throw new IllegalStateException("Auth data not initialized. Ensure @WithLogin is used");
//        }
//        return AUTH_DATA.get();
//    }
//
//    @Override
//    public void beforeEach(ExtensionContext context) {
//        // 1. Выполняем авторизацию через API
//        Response response = given()
//                .spec(RequestSpec)
//                .body(String.format(
//                        "{\"userName\":\"%s\",\"password\":\"%s\"}",
//                        TestData.login,
//                        TestData.password
//                ))
//                .post("/Account/v1/Login")
//                .then()
//                .spec(Response200Spec)
//                .extract()
//                .response();
//
//        // 2. Сохраняем данные авторизации
//        AUTH_DATA.set(new AuthData(
//                response.path("userId"),
//                response.path("token"),
//                response.path("expires")
//        ));
//
//        // 3. Настраиваем браузер
//        configureBrowser();
//
//        // 4. Устанавливаем куки
//        open("/favicon.ico");
//        WebDriverRunner.getWebDriver().manage().addCookie(
//                new Cookie("userID", response.path("userId"))
//        );
//        WebDriverRunner.getWebDriver().manage().addCookie(
//                new Cookie("token", response.path("token"))
//        );
//    }
//
//    @Override
//    public void afterEach(ExtensionContext context) {
//        // 5. Очищаем данные и закрываем драйвер
//        AUTH_DATA.remove();
//        Selenide.closeWebDriver();
//    }
//
//    private void configureBrowser() {
//        ChromeOptions options = new ChromeOptions()
//                .addArguments(
//                        "--no-sandbox",
//                        "--disable-dev-shm-usage",
//                        "--remote-allow-origins=*",
//                        "--user-data-dir=/tmp/chrome-" + UUID.randomUUID()
//                );
//
//        WebDriverRunner.setWebDriver(new ChromeDriver(options));
//    }
//
//    public record AuthData(
//            String userId,
//            String token,
//            String expires
//    ) {}
