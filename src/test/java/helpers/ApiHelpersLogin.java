package helpers;

import models.LoginRequestModel;
import models.LoginResponseModel;

import static com.codeborne.selenide.Selenide.open;
import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static specs.SpecsList.*;
import static tests.TestData.*;

public class ApiHelpersLogin {
    public static LoginResponseModel loginRequest() {
        System.out.println("Sending login request to: " + "https://demoqa.com/Account/v1/Login");
        LoginRequestModel authData = new LoginRequestModel(login, password);

        return given(bookRequestSpec(token))
                .baseUri("https://demoqa.com")
                .filter(withCustomTemplates())
                .contentType(JSON)
                .log().all()
                .body(authData)
                .when()
                .post("/Account/v1/Login")
                .then()
                .spec(authResponseSpec(200))
                .extract().as(LoginResponseModel.class);
    }
}
