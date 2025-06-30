package page;

import com.codeborne.selenide.SelenideElement;
import tests.TestData;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class LoginForm {

    private final SelenideElement

            userTitle = $("#userName-value");

    public void checkResultNamePage() {
        userTitle.shouldHave(text(TestData.login));

    }
    public LoginForm openProfilePage(){
        open("/profile");
        return this;
    }
}
