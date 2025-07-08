package pages;


import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static tests.TestData.title;


public class ProfilePage {
    private final SelenideElement
            booksTable = $(".ReactTable");


    @Step("Открываем страницу профиля")
    public ProfilePage openPage() {
        open("/profile");
        return this;
    }

    @Step("Проверяем отсутствие книги с названием '{title}'")
    public ProfilePage verifyBookNotPresentByTitle(String title) {
        // Проверяем что ни одна строка таблицы не содержит указанное название
        booksTable.shouldNotBe(text(title));
        return this;
    }


}