package helpers;

import helpers.WithLoginExtension;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static specs.SpecsList.*;

public class ApiHelper {


// Удаляет все книги пользователя
    public static void deleteAllBooks() {
        // Получаем данные из authResponse через jsonPath()
        String userId = WithLoginExtension.authResponse.jsonPath().getString("userId");
        String token = WithLoginExtension.authResponse.jsonPath().getString("token");

        given()
                .spec(RequestSpec)
                .header("Authorization", "Bearer " + token)
                .queryParam("UserId", userId)
                .when()
                .delete("/BookStore/v1/Books")
                .then()
                .spec(Response204Spec);
    }

// Добавляет книгу по ISBN
public static void addBook(String isbn) {
    String bookData = format("{\"userId\":\"%s\",\"collectionOfIsbns\":[{\"isbn\":\"%s\"}]}",
            WithLoginExtension.authResponse.path("userId"), isbn);

    given(RequestSpec)
            .header("Authorization", "Bearer " + WithLoginExtension.authResponse.path("token"))
            .body(bookData)
            .when()
            .post("/BookStore/v1/Books")
            .then()
            .spec(Response201Spec);
}

// Удаляет конкретную книгу по ISBN
public static void deleteBook(String isbn) {
    String deleteBookData = format("{\"userId\":\"%s\",\"isbn\":\"%s\"}",
            WithLoginExtension.authResponse.path("userId"), isbn);

    given(RequestSpec)
            .header("Authorization", "Bearer " + WithLoginExtension.authResponse.path("token"))
            .body(deleteBookData)
            .when()
            .delete("/BookStore/v1/Book")
            .then()
            .spec(Response204Spec);
}

// Обновляет куки после действий
public static void refreshCookies() {
    open("/favicon.ico");
    getWebDriver().manage().addCookie(new Cookie("userID", WithLoginExtension.authResponse.path("userId")));
    getWebDriver().manage().addCookie(new Cookie("expires", WithLoginExtension.authResponse.path("expires")));
    getWebDriver().manage().addCookie(new Cookie("token", WithLoginExtension.authResponse.path("token")));
}
}