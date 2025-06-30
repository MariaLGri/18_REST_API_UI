package page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;


public class Basket {
    private SelenideElement

            bookTitle = $(".ReactTable");


    public void checkResultNameBook() {
        bookTitle.shouldHave(text("Git Pocket Guide"));

    }
    public void checkResultNotNameBook() {
        bookTitle.shouldNotHave(text("Git Pocket Guide"));

    }
}