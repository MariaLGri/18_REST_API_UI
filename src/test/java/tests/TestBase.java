package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;

import helpers.Attach;

import java.util.Map;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class TestBase {


    @BeforeAll
    static void setup() {

        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = System.getProperty("screenResolution", "1920x1080");
        Configuration.browser = System.getProperty("browser", "chrome");
        Configuration.browserVersion = System.getProperty("browserVersion", "127.0");
        Configuration.pageLoadStrategy = "eager";
        Configuration.timeout = 10000;
        Configuration.remote = String.format(
                "https://%s:%s@%s/wd/hub",
                System.getProperty("selenoidUserLogin", "user1"),
                System.getProperty("selenoidUserPassword", "1234"),
                System.getProperty("selenoidUrl", "selenoid.autotests.cloud")
        );
               DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", true,
                "enableVideo", true
        ));
        Configuration.browserCapabilities = capabilities;

        RestAssured.baseURI = "https://demoqa.com";
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

    @BeforeEach
    void setUpSettings(){
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterEach
    void shutDown() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        // Добавляем логи и видео только для удалённого запуска
        if (Configuration.remote != null) {
            Attach.browserConsoleLogs();
            Attach.addVideo();
        }
        closeWebDriver();
    }

}