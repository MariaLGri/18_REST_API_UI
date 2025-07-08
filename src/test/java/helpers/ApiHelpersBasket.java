package helpers;

import models.AddBookRequestModel;
import models.DeleteBookRequestModel;
import tests.TestData;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static specs.SpecsList.*;
import static tests.TestData.*;

public class ApiHelpersBasket {

    public static void deleteAllBooks() {

        given()
                .spec(RequestSpec(token))
                .when()
                .delete(BookStoreBooks + "?UserId=" + TestData.userId)
                .then()
                .spec(bookResponseSpec(204));
    }

    public static void addBook(AddBookRequestModel addBookData) {

        given(RequestSpec(token))
                .body(addBookData)
                .when()
                .post(BookStoreBooks)
                .then()
                .spec(bookResponseSpec(201));
    }

    public static void deleteBook(DeleteBookRequestModel deleteBookData) {
        given(RequestSpec(token))
                .baseUri(baseURI)
                .body(deleteBookData)
                .when()
                .delete(BookStoreBook)
                .then()
                .spec(bookResponseSpec(204));
    }

}



