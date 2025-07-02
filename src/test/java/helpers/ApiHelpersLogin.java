package helpers;

import models.LoginRequestModel;
import models.LoginResponseModel;

import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;
import static specs.SpecsList.*;
import static tests.TestData.*;

public class ApiHelpersLogin {
    public static LoginResponseModel loginRequest() {
        System.out.println("Sending login request to: " + "https://demoqa.com" + "/Account/v1/Login");
        LoginRequestModel authData = new LoginRequestModel(login, password);

        return given(authRequestSpec)
                .body(authData)
                .when()
                .post(loginAccount)
                .then()
                .spec(authResponseSpec(200))
                .extract().as(LoginResponseModel.class);
    }
}
