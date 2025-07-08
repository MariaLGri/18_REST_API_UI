package models;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.Data;

@Data
public class LoginResponseModel {
    private static final Dotenv dotenv = Dotenv.load();
    // Получаем логин и пароль из .env
    String password = dotenv.get("DEMOQA_PASSWORD");
    String userId, username, token, expires, isActive;

    @JsonProperty("created_date")
    String createdDate;
}