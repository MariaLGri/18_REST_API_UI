package helpers;

import io.qameta.allure.Step;
import models.LoginResponseModel;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static tests.TestData.*;

public class WithLoginExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) {
        if (context.getTestMethod().isPresent() &&
                context.getTestMethod().get().isAnnotationPresent(WithLogin.class)) {

            authorizeAndSetCookies();
        }

    }

    @Step("Авторизация через API и установка cookies")
    private void authorizeAndSetCookies() {
        LoginResponseModel auth = ApiHelpersLogin.loginRequest();

        userId = auth.getUserId();
        token = auth.getToken();
        expires = auth.getExpires();

        open("/favicon.ico");

        getWebDriver().manage().addCookie(new Cookie("userID", userId));
        getWebDriver().manage().addCookie(new Cookie("expires", expires));
        getWebDriver().manage().addCookie(new Cookie("token", token));
    }

}