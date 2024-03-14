import com.github.javafaker.Faker;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class User {
    private final Faker faker = new Faker();
    private final String email = randomEmail();
    private final String secondEmail = randomEmail();
    private final String password = randomPassword();
    private final String secondPassword = randomPassword();
    private final String name = randomName();
    private final String secondName = randomName();
    private String randomEmail() {
        return faker.internet().emailAddress();
    }

    private String randomPassword() {
        return faker.internet().password();
    }

    private String randomName() {
        return faker.name().username();
    }

    protected Response createRandomUser() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("email", email);
        requestBody.put("password", password);
        requestBody.put("name", name);
        return given()
                .spec(BuildAPI.baseRequestSpec())
                .body(requestBody)
                .post(BuildAPI.CREATE_USER_ENDPOINT)
                .thenReturn();
    }

    protected Response createRandomUserWithoutEmail() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("password", password);
        requestBody.put("name", name);
        return given()
                .spec(BuildAPI.baseRequestSpec())
                .body(requestBody)
                .post(BuildAPI.CREATE_USER_ENDPOINT)
                .thenReturn();
    }

    protected Response createRandomUserWithoutPassword() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("email", email);
        requestBody.put("password", "");
        requestBody.put("name", name);
        return given()
                .spec(BuildAPI.baseRequestSpec())
                .body(requestBody)
                .post(BuildAPI.CREATE_USER_ENDPOINT)
                .thenReturn();
    }

    protected Response createRandomUserWithoutName() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("email", email);
        requestBody.put("password", password);
        requestBody.put("name", "");
        return given()
                .spec(BuildAPI.baseRequestSpec())
                .body(requestBody)
                .post(BuildAPI.CREATE_USER_ENDPOINT)
                .thenReturn();
    }

    protected Response loginUser() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("email", email);
        requestBody.put("password", password);
        requestBody.put("name", name);
        return given()
                .spec(BuildAPI.baseRequestSpec())
                .body(requestBody)
                .post(BuildAPI.LOGIN_USER_ENDPOINT)
                .thenReturn();
    }

    protected Response loginUserWithIncorrectLogin() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("email", secondEmail);
        requestBody.put("password", password);
        requestBody.put("name", name);
        return given()
                .spec(BuildAPI.baseRequestSpec())
                .body(requestBody)
                .post(BuildAPI.LOGIN_USER_ENDPOINT)
                .thenReturn();
    }

    protected Response loginUserWithIncorrectPassword() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("email", email);
        requestBody.put("password", secondPassword);
        requestBody.put("name", name);
        return given()
                .spec(BuildAPI.baseRequestSpec())
                .body(requestBody)
                .post(BuildAPI.LOGIN_USER_ENDPOINT)
                .thenReturn();
    }

    protected Response changeUser(String accessToken) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("email", secondEmail);
        requestBody.put("password", secondPassword);
        requestBody.put("name", secondName);
        return given()
                .spec(BuildAPI.baseRequestSpec())
                .header("Authorization", accessToken)
                .body(requestBody)
                .patch(BuildAPI.CHANGE_USER_ENDPOINT)
                .thenReturn();
    }

    protected Response changeUserWithoutAuthorization() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("email", secondEmail);
        requestBody.put("password", secondPassword);
        requestBody.put("name", secondName);
        return given()
                .spec(BuildAPI.baseRequestSpec())
                .body(requestBody)
                .patch(BuildAPI.CHANGE_USER_ENDPOINT)
                .thenReturn();
    }

    protected Response changeUserEmail(String accessToken) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("email", secondEmail);
        return given()
                .spec(BuildAPI.baseRequestSpec())
                .header("Authorization", accessToken)
                .body(requestBody)
                .patch(BuildAPI.CHANGE_USER_ENDPOINT)
                .thenReturn();
    }

    protected Response changeUserPassword(String accessToken) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("password", secondPassword);
        return given()
                .spec(BuildAPI.baseRequestSpec())
                .header("Authorization", accessToken)
                .body(requestBody)
                .patch(BuildAPI.CHANGE_USER_ENDPOINT)
                .thenReturn();
    }

    protected Response changeUserName(String accessToken) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", secondName);
        return given()
                .spec(BuildAPI.baseRequestSpec())
                .header("Authorization", accessToken)
                .body(requestBody)
                .patch(BuildAPI.CHANGE_USER_ENDPOINT)
                .thenReturn();
    }

    protected Response deleteUser(String accessToken) {
        return given()
                .spec(BuildAPI.baseRequestSpec())
                .header("Authorization", accessToken)
                .delete(BuildAPI.CHANGE_USER_ENDPOINT);
    }
}
