package tests;

import helpers.ApiHelpersBasket;
import helpers.WithLogin;
import models.AddBookRequestModel;
import models.DeleteBookRequestModel;
import models.IsbnModel;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Test;
import pages.ProfilePage;

import java.util.List;
import static com.codeborne.selenide.logevents.SelenideLogger.step;
import static tests.TestData.*;
@DisplayName("Тесты на удаление книг")
public class ShoppingCartsTest extends TestBase {
    ProfilePage profilePage = new ProfilePage();
    @DisplayName("Удаление книги из корзины")
    @WithLogin
    @Test
    void deleteBookTest() {

        List<IsbnModel> isbns = List.of(
                new IsbnModel(bookISBN_1),
                new IsbnModel(bookISBN_2)
        );

        AddBookRequestModel addBookData = new AddBookRequestModel(userId, isbns);
        DeleteBookRequestModel deleteBookData = new DeleteBookRequestModel(userId, bookISBN_1);

        step("Удаление всех имеющихся в корзине книг", ApiHelpersBasket::deleteAllBooks);

        step("Добавление книги в корзину", () ->
                ApiHelpersBasket.addBook(addBookData));

        step("Удаление книги из корзины", () -> {

            ApiHelpersBasket.deleteBook(deleteBookData);
        });

        profilePage
                .openPage()

                .verifyBookNotPresentByTitle(title);
    }
}