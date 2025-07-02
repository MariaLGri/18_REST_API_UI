package helpers;

import models.*;
import tests.TestData;

import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;
import static specs.SpecsList.*;
import static tests.TestData.*;

public class ApiHelpersBasket {

        public static void deleteAllBooks () {

            given()
                    .spec(bookRequestSpec(token))
                    .when()
                    .delete(BookStoreBooks+ "?UserId=" + userId)
                    .then()
                    .spec(bookResponseSpec(204));
        }

        public static void addBook (AddBookRequestModel addBookData){

            given()
                    .spec(bookRequestSpec(token))
                    .body(addBookData)
                    .when()
                    .post(BookStoreBooks)
                    .then()
                    .spec(bookResponseSpec(201));
        }

        public static void deleteBook (DeleteBookRequestModel deleteBookData){
            given()
                    .spec(bookRequestSpec(token))
                    .when()
                    .delete(BookStoreBook)
                    .then()
                    .spec(bookResponseSpec(204));
        }

}








//       public static void deleteAllBooks() {
//        LoginResponseModel response = WithLoginExtension.getAuthData();
//        String userId = response.getUserId();
//        String token = response.getToken();
//
//        given()
//                .spec(RequestSpec)
//                .header("Authorization", "Bearer " + token)
//                .queryParam("UserId", userId)
//                .when()
//                .delete("/BookStore/v1/Books")
//                .then()
//                .spec(Response204Spec);
//    }

    // Добавляет книгу по ISBN
//    public static void addBook(String isbn) {
//        LoginResponseModel authData = WithLoginExtension.getAuthData();
//
//        // Вариант 1: Через конструктор (если он есть)
//        AddBookRequestModel requestBody = new AddBookRequestModel(
//                authData.getUserId(),
//                new BookIsbnModel[] { new BookIsbnModel(isbn) }
//        );
//        // Отправляем запрос
//        given()
//                .spec(RequestSpec)
//                .header("Authorization", "Bearer " + authData.getToken())
//                .body(requestBody)
//                .when()
//                .post("/BookStore/v1/Books")
//                .then()
//                .spec(Response201Spec);
//    }
//
//    // Удаляет конкретную книгу по ISBN
//    public static void deleteBook(String isbn) {
//        LoginResponseModel authData = WithLoginExtension.getAuthData();
//
//        // Формируем тело запроса через Map (альтернатива JSON-строке)
//        Map<String, Object> requestBody = new HashMap<>();
//        requestBody.put("userId", authData.getUserId());
//        requestBody.put("isbn", isbn);
//
//        given()
//                .spec(RequestSpec)
//                .header("Authorization", "Bearer " + authData.getToken())
//                .body(requestBody)
//                .when()
//                .delete("/BookStore/v1/Book")
//                .then()
//                .spec(Response204Spec);
//    }
//
//    // Обновляет куки после действий
//    public static void refreshCookies() {
//        LoginResponseModel authData = WithLoginExtension.getAuthData();
//
//        open("/favicon.ico");
//        WebDriver driver = getWebDriver();
//
//        // Устанавливаем куки
//        driver.manage().addCookie(new Cookie("userID", authData.getUserId()));
//        driver.manage().addCookie(new Cookie("token", authData.getToken()));
//
//        if (authData.getExpires() != null) {
//            driver.manage().addCookie(new Cookie("expires", authData.getExpires()));
//        }
//    }

