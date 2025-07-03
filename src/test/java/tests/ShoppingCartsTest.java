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

public class ShoppingCartsTest extends TestBase {

    @DisplayName("Удаление книги из корзины")
    @WithLogin
    @Test
    void DeleteBookTest() {
        ProfilePage profilePage = new ProfilePage();

        IsbnModel isbn = new IsbnModel(bookISBN);
        List<IsbnModel> isbns = List.of(isbn);

        AddBookRequestModel addBookData = new AddBookRequestModel(userId, isbns);

        DeleteBookRequestModel deleteBookData = new DeleteBookRequestModel(userId, bookISBN);

        step("Удаление всех имеющихся в корзине книг", ApiHelpersBasket::deleteAllBooks);

        step("Добавление книги в корзину", () ->
                addBook(addBookData));

        step("Удаление добавленной книги из корзины", () ->{
                        ApiHelpersBasket.deleteBook(deleteBookData);
        });

        profilePage.openPage()
                .checkTableEmpty();
    }
}