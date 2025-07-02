package page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class ProfilePage {
    private SelenideElement

            bookTitle = $(".ReactTable");

    @Step("Открываем страницу профиля")
    public ProfilePage openPage() {
        open("/profile");
        return this;
    }

    @Step("Проверяем пустоту таблицы, без книг")
    public ProfilePage checkTableEmpty() {
        bookTitle.shouldBe(visible);
        return this;
    }
}