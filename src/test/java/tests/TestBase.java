package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.*;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

public class TestBase {

    @BeforeAll
    static void setup() {
        // Browser config
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1920x1080";
        Configuration.pageLoadStrategy = "eager";
        Configuration.timeout = 10000;
        Configuration.holdBrowserOpen = false;

        // Selenoid config
        configureRemoteDriver();

        // RestAssured config
        RestAssured.baseURI = "https://demoqa.com";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @BeforeEach
    void initTest() {
        SelenideLogger.addListener("allure", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true)
        );
    }

    @AfterEach
    void tearDown() {
        // Сначала делаем все аттачи
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();

        // Только потом закрываем браузер
        Selenide.closeWebDriver();

        // Для Selenoid (если используется)
        if (Configuration.remote != null) {
            Attach.addVideo();
        }
    }

    private static void configureRemoteDriver() {
        String selenoidUrl = System.getProperty("selenoid.url");
        if (selenoidUrl != null) {
            Configuration.remote = String.format(
                    "https://%s:%s@%s/wd/hub",
                    System.getProperty("selenoid.login", "user1"),
                    System.getProperty("selenoid.password", "1234"),
                    selenoidUrl
            );

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("selenoid:options", Map.of(
                    "enableVNC", true,
                    "enableVideo", true
            ));
            Configuration.browserCapabilities = capabilities;
        }
    }
}