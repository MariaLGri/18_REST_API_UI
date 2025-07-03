package tests;

import helpers.ApiHelpersBasket;
import helpers.WithLogin;

import models.*;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Test;
import page.ProfilePage;

import java.util.List;


import static com.codeborne.selenide.logevents.SelenideLogger.step;
import static helpers.ApiHelpersBasket.*;
import static tests.TestData.*;
@DisplayName("Тесты на удаление книг")
public class ShoppingCartsTest extends TestBase {

    @DisplayName("Удаление книги из корзины")
    @WithLogin
    @Test
    void DeleteBookTest() {
        ProfilePage profilePage = new ProfilePage();

//        IsbnModel isbn = new IsbnModel();
        List<IsbnModel> isbns = List.of(new IsbnModel(bookISBN_1),
                new IsbnModel(bookISBN_2));

        AddBookRequestModel addBookData = new AddBookRequestModel(userId, isbns);

        DeleteBookRequestModel deleteBookData = new DeleteBookRequestModel(userId, bookISBN_1);

        step("Удаление всех имеющихся в корзине книг ", ApiHelpersBasket::deleteAllBooks);

        step("Добавление книги в корзину", () ->
                addBook(addBookData));

        step("Удаление одной из добавленных книги из корзины ", () -> {
            ApiHelpersBasket.deleteBook(deleteBookData);
        });


        profilePage.openPage()
                .verifyBookNotPresentByTitle();
    }
}