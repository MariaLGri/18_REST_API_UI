package page;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;


public class Basket {
    private SelenideElement

            bookTitle = $(".ReactTable");


    public void checkResultNameBook() {
        bookTitle.shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Git Pocket Guide"));

    }
    public void checkResultNotNameBook() {
        bookTitle.shouldNotHave(text("Git Pocket Guide"));

    }
}