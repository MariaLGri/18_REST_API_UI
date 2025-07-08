package config;

import com.codeborne.selenide.Configuration;
import io.github.cdimascio.dotenv.Dotenv;
import io.restassured.RestAssured;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

public class ProjectConfig {
   public static final Dotenv dotenv = Dotenv.load();

    public static String getLogin() {
        String login = dotenv.get("DEMOQA_LOGIN");
        if (login == null) throw new RuntimeException("DEMOQA_LOGIN не найден в .env");
        return login;
    }

    public static String getPassword() {
        String password = dotenv.get("DEMOQA_PASSWORD");
        if (password == null) throw new RuntimeException("DEMOQA_PASSWORD не найден в .env");
        return password;
    }

    private final WebConfig webConfig;

    public ProjectConfig(WebConfig webConfig) {
        this.webConfig = webConfig;
    }

    public void apiConfig() {
        RestAssured.baseURI = webConfig.getBaseUrl();
    }

    public void webConfig() {
        Configuration.baseUrl = webConfig.getBaseUrl();
        Configuration.browser = webConfig.getBrowser().toString();
        Configuration.browserVersion = webConfig.getBrowserVersion();
        Configuration.browserSize = webConfig.getBrowserSize();
        if (webConfig.isRemote()) {
            Configuration.remote = webConfig.getRemoteUrl();
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                    "enableVNC", true,
                    "enableVideo", true
            ));
            Configuration.browserCapabilities = capabilities;
        }
    }



}
