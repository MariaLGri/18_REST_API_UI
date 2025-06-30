package tests;

import helpers.ApiHelper;
import helpers.WithLogin;
import helpers.WithLoginExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import page.Basket;
import page.LoginForm;
import static helpers.ApiHelper.refreshCookies;
import static io.qameta.allure.Allure.step;

public class BookСollectionTest extends TestBase {

    @Test
    @DisplayName("Проверка добавления и удаления книг /одной книги из корзины")
    @WithLogin
    void addBookToCollection_withDelete_1_Book_BooksTest() {
        ApiHelper apiHelper = new ApiHelper();
        LoginForm loginForm = new LoginForm();
        Basket basket = new Basket();

        String isbn = "9781449325862";

        step("Удаляем все имеющиеся в корзине книги", () -> apiHelper.deleteAllBooks());

        step("Добавляем книгу", () -> apiHelper.addBook(isbn));

        step("Проверяем, что книга добавлена", () -> {
            loginForm.openProfilePage();
            basket.checkResultNameBook();
        });

        step("Удаляем добавленную книгу из корзины", () -> apiHelper.deleteBook(isbn));

        refreshCookies();

        step("Проверяем отсутствие книги в корзине после удаления", () -> {
            loginForm.openProfilePage();
            basket.checkResultNotNameBook();
        });
    }


}
