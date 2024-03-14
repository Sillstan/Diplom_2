import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreateUserTest {
    private User user;
    private int statusCode;
    private String accessToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = BuildAPI.URL;
        user = new User();
    }

    @Test
    @DisplayName("Создание уникального пользователя")
    public void testCreateUniqUser() {
        Response response = user.createRandomUser();
        statusCode = response.getStatusCode();
        assertEquals(200, statusCode);
        accessToken = response.jsonPath().getString("accessToken");
        assertTrue(response.getBody().jsonPath().getBoolean("success"));
    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован")
    public void testCreatIdencialUsers() {
        Response response = user.createRandomUser();
        statusCode = response.getStatusCode();
        accessToken = response.jsonPath().getString("accessToken");
        Response response2 = user.createRandomUser();
        assertEquals(403, response2.getStatusCode());
        assertFalse(response2.getBody().jsonPath().getBoolean("success"));
    }

    @Test
    @DisplayName("Создание пользователя без электронной почты")
    public void testCreateUserWithoutEmail() {
        Response response = user.createRandomUserWithoutEmail();
        statusCode = response.getStatusCode();
        assertEquals(403, response.getStatusCode());
        assertFalse(response.getBody().jsonPath().getBoolean("success"));
        if (statusCode == 200) {
            accessToken = response.jsonPath().getString("accessToken");
        }
    }

    @Test
    @DisplayName("Создание пользователя без пароля")
    public void testCreatUserWithoutPassword() {
        Response response = user.createRandomUserWithoutPassword();
        statusCode = response.getStatusCode();
        assertEquals(403, response.getStatusCode());
        assertFalse(response.getBody().jsonPath().getBoolean("success"));
        if (statusCode == 200) {
            accessToken = response.jsonPath().getString("accessToken");
        }
    }

    @Test
    @DisplayName("Создание пользователя без имени")
    public void testCreateUserWithoutName() {
        Response response = user.createRandomUserWithoutName();
        statusCode = response.getStatusCode();
        assertEquals(403, response.getStatusCode());
        assertFalse(response.getBody().jsonPath().getBoolean("success"));
        if (statusCode == 200) {
            accessToken = response.jsonPath().getString("accessToken");
        }
    }

    @After
    public void tearDown() {
        if (statusCode == 200) {
            user.deleteUser(accessToken);
        }
    }

}
