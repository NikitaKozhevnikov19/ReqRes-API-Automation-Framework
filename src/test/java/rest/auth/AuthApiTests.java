package rest.auth;

import models.ErrorResponse;
import models.AuthResponse;
import models.UserEmailDto;
import models.UserAuthDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import rest.ApiTestBase;
import spec.ReqresSpecs;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Tag("auth")
public class AuthApiTests extends ApiTestBase {

    private static final String LOGIN_URL = "/login";

    @Test
    @DisplayName("Успешный логин")
    void shouldLoginSuccessfully() {
        UserAuthDto validUser = new UserAuthDto("eve.holt@reqres.in", "cityslicka");

        AuthResponse response = step("Отправить запрос на логин", () ->
                given()
                        .spec(ReqresSpecs.requestSpecification)
                        .body(validUser)
                        .when()
                        .post(LOGIN_URL)
                        .then()
                        .spec(ReqresSpecs.responseSpec(200))
                        .extract().as(AuthResponse.class));

        step("Проверить наличие токена", () ->
                assertNotNull(response.getToken()));
    }

    @Test
    @DisplayName("Негативный тест: отсутствие пароля")
    void shouldReturn400OnMissingPassword() {
        UserEmailDto onlyEmail = new UserEmailDto("sydney@fife");

        ErrorResponse response = step("Попытка логина без пароля", () ->
                given()
                        .spec(ReqresSpecs.requestSpecification)
                        .body(onlyEmail)
                        .when()
                        .post("/register")
                        .then()
                        .spec(ReqresSpecs.responseSpec(400))
                        .extract().as(ErrorResponse.class));

        step("Проверить текст ошибки", () ->
                assertEquals("Missing password", response.getError()));
    }
}
