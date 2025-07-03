package helpers;

import io.restassured.RestAssured;
import models.*;
import tests.TestData;

import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static specs.SpecsList.*;
import static tests.TestData.*;

public class ApiHelpersBasket {

    public static void deleteAllBooks() {

        given()
                .spec(bookRequestSpec(token))
                .when()
                .delete(BookStoreBooks + "?UserId=" + userId)
                .then()
                .spec(bookResponseSpec(204));
    }

    public static void addBook(AddBookRequestModel addBookData) {

        given(bookRequestSpec(token))
                .body(addBookData)
                .when()
                .post(BookStoreBooks)
                .then()
                .spec(bookResponseSpec(201));
    }

    public static void deleteBook(DeleteBookRequestModel deleteBookData) {
        given(bookRequestSpec(token))
                .baseUri(baseURI)
                .body(deleteBookData)
                .when()
                .delete(BookStoreBook)
                .then()
                .spec(bookResponseSpec(204));
    }

}



