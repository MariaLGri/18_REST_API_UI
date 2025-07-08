package helpers;

import models.GenerateTokenResponseModel;
import models.LoginResponseModel;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static tests.TestData.*;

//public class WithLoginExtension implements BeforeEachCallback {
//    @Override
//    public void beforeEach(ExtensionContext context) {
//        if (context.getTestMethod().isPresent() &&
//                context.getTestMethod().get().isAnnotationPresent(WithLogin.class)) {
//
//            authorizeAndSetCookies();
//        }
//
//    }
//
//    @Step("Авторизация через API и установка cookies")
//    private void authorizeAndSetCookies() {
//        LoginResponseModel auth = ApiHelpersLogin.loginRequest();
//
//
//        userId = auth.getUserId();
//        token = auth.getToken();
//        expires = auth.getExpires();
//
//        open("/favicon.ico");
//
//        getWebDriver().manage().addCookie(new Cookie("userID", userId));
//        getWebDriver().manage().addCookie(new Cookie("expires", expires));
//        getWebDriver().manage().addCookie(new Cookie("token", token));
//    }
//    @Step("Обновление токена")
//    public String refreshToken() {
//        // Вызываем API авторизации для получения нового токена
//        LoginResponseModel auth = ApiHelpersLogin.loginRequest();
//        return auth.getToken();
//    }
//}


public class WithLoginExtension implements BeforeEachCallback {


    @Override
    public void beforeEach(ExtensionContext context) {
        if (testRequiresAuth(context)) {
            refreshAuthData();
        }
    }

    private boolean testRequiresAuth(ExtensionContext context) {
        return context.getTestMethod()
                .filter(m -> m.isAnnotationPresent(WithLogin.class))
                .isPresent();
    }

    private void refreshAuthData() {
        fallbackToFullLogin();
        setCookies();
    }

    private void updateFromTokenResponse(GenerateTokenResponseModel response) {
        token = response.getToken();
        expires = response.getExpires();
        // userId не обновляется, так как GenerateToken его не возвращает
    }

    private void fallbackToFullLogin() {
        LoginResponseModel authResponse = ApiHelpersLogin.loginRequest();
        userId = authResponse.getUserId();
        token = authResponse.getToken();
        expires = authResponse.getExpires();
    }

    private void setCookies() {
        open("/favicon.ico");
        getWebDriver().manage().deleteAllCookies();

        System.out.println("Setting cookies: userId=" + userId + ", token=" + token + ", expires=" + expires);

        if (userId == null || token == null || expires == null) {
            throw new IllegalStateException("Auth data is missing! userId=" + userId +
                    ", token=" + token + ", expires=" + expires);
        }

        getWebDriver().manage().addCookie(new Cookie("userID", userId));
        getWebDriver().manage().addCookie(new Cookie("token", token));
        getWebDriver().manage().addCookie(new Cookie("expires", expires));
    }


}