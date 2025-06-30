package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import page.LoginForm;

import helpers.WithLogin;


public class LoginTest extends TestBase {
    LoginForm loginForm = new LoginForm();

    @Test
    @DisplayName("Проверка запроса на авторизацию")
    @WithLogin
    void
    authorizationApiTest() {
        loginForm.openProfilePage()
                .checkResultNamePage();
    }
}
