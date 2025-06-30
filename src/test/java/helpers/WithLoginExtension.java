package helpers;

import helpers.ApiHelper.AccountApiRequests;
import models.LoginResponseModel;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;



public class WithLoginExtension implements BeforeEachCallback {

    private static final ThreadLocal<LoginResponseModel> AUTH_DATA = new ThreadLocal<>();

    public static LoginResponseModel getAuthData() {
        return AUTH_DATA.get();
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        LoginResponseModel response = ApiHelper.AccountApiRequests.loginRequest();
        AUTH_DATA.set(response);

        step("Авторизация с  @WithLogin", () ->
                open("/favicon.ico"));
        getWebDriver().manage().addCookie(new Cookie("userID", response.getUserId()));
        getWebDriver().manage().addCookie(new Cookie("token", response.getToken()));
//        getWebDriver().manage().addCookie(new Cookie("token", response.expires()));
    }
}
