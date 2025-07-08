package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.ConfigReader;
import config.ProjectConfig;
import config.WebConfig;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import helpers.Attach;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class TestBase {

    private static final WebConfig webConfig = ConfigReader.Instance.read();
    @BeforeAll

    public static void setUpSettings() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        ProjectConfig projectConfiguration = new ProjectConfig(webConfig);
        projectConfiguration.webConfig();
        projectConfiguration.apiConfig();
        Configuration.pageLoadStrategy = "eager";
        Configuration.timeout = 10000;
    }


    @BeforeEach
    void setUpSettingsEach(){
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


