package rest.auth;

import models.EmailResponseError;
import models.RegisterResponse;
import models.ResponseEmail;
import models.UserRegistration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spec.SpecCustoms;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;


@Tag("auth")
public class AuthApiTests extends rest.TestBase {

    private static final String LOGIN_URL = "/login";

    @Test
    void shouldLoginSuccessfully() {
        UserRegistration validUser = new UserRegistration(
                "eve.holt@reqres.in",
                "cityslicka"
        );

        RegisterResponse response = step("Login request", () ->
                given()
                        .spec(SpecCustoms.requestSpecification)
                        .body(validUser)
                        .when()
                        .post(LOGIN_URL)
                        .then()
                        .spec(SpecCustoms.responseSpecificationBuilder(200))
                        .extract().as(RegisterResponse.class)
        );

        step("Verify token", () -> {
            String token = response.getToken();

            assertAll(
                    () -> assertNotNull(token, "Token should not be null"),
                    () -> assertFalse(token.isBlank(), "Token should not be blank"),
                    () -> assertTrue(token.length() >= 10, "Token length should be >= 10"),
                    () -> assertTrue(token.matches("^[a-zA-Z0-9]+$"),
                            "Token should be alphanumeric")
            );
        });
    }

    @Test
    void shouldReturn400OnMissingPassword() {
        ResponseEmail onlyEmail = new ResponseEmail("peter@klaven");

        EmailResponseError response = step("Request with no password", () ->
                given()
                        .spec(SpecCustoms.requestSpecification)
                        .body(onlyEmail)
                        .when()
                        .post(LOGIN_URL)
                        .then()
                        .spec(SpecCustoms.responseSpecificationBuilder(400))
                        .extract().as(EmailResponseError.class)
        );

        step("Verify error message", () -> assertEquals("Missing password", response.getError()));
    }
}
