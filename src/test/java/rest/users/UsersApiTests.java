package rest.users;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import rest.ApiTestBase;
import spec.ReqresSpecs;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Tag("users")
@Epic("API Reqres.in")
@Feature("CRUD операции пользователя")
public class UsersApiTests extends ApiTestBase {

    private static final String URL = "/users";

    @Test
    @DisplayName("GET: Список пользователей (Read)")
    void getListUsersTest() {
        UserListResponse response = step("Запросить список пользователей", () ->
                given()
                        .spec(ReqresSpecs.requestSpecification)
                        .queryParam("page", "2")
                        .when()
                        .get(URL)
                        .then()
                        .spec(ReqresSpecs.responseSpec(200))
                        .extract().as(UserListResponse.class));

        step("Проверить email второго пользователя", () ->
                assertThat(response.getData().get(1).getEmail())
                        .isEqualTo("lindsay.ferguson@reqres.in"));
    }

    @Test
    @DisplayName("POST: Создание пользователя (Create)")
    void shouldCreateUser() {
        UserDto user = new UserDto("morpheus", "leader");

        UserCreateResponse response = step("Создать нового пользователя", () ->
                given()
                        .spec(ReqresSpecs.requestSpecification)
                        .body(user)
                        .when()
                        .post(URL)
                        .then()
                        .spec(ReqresSpecs.responseSpec(201))
                        .extract().as(UserCreateResponse.class));

        step("Проверить имя созданного пользователя", () ->
                assertEquals("morpheus", response.getName()));
    }

    @Test
    @DisplayName("PUT: Обновление данных (Update)")
    void shouldUpdateUser() {
        UserDto user = new UserDto("morpheus", "zion resident");

        UserUpdateResponse response = step("Обновить пользователя через PUT", () ->
                given()
                        .spec(ReqresSpecs.requestSpecification)
                        .body(user)
                        .when()
                        .put(URL + "/2")
                        .then()
                        .spec(ReqresSpecs.responseSpec(200))
                        .extract().as(UserUpdateResponse.class));

        step("Проверить обновленную работу", () ->
                assertEquals("zion resident", response.getJob()));
    }

    @Test
    @DisplayName("DELETE: Удаление пользователя (Delete)")
    void shouldDeleteUser() {
        step("Удалить пользователя с ID 2", () ->
                given()
                        .spec(ReqresSpecs.requestSpecification)
                        .when()
                        .delete(URL + "/2")
                        .then()
                        .spec(ReqresSpecs.responseSpec(204)));
    }
}
