import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ChangeUserTest {
    private User user;
    private int statusCode;
    private String accessToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = BuildAPI.URL;
        user = new User();
        Response response = user.createRandomUser();
        statusCode = response.getStatusCode();
        accessToken = response.jsonPath().getString("accessToken");
    }

    @Test
    @DisplayName("Изменение всех данных пользователя")
    public void testChangeUser() {
        Response response = user.changeUser(accessToken);
        assertEquals(200, response.getStatusCode());
        assertTrue(response.getBody().jsonPath().getBoolean("success"));
    }

    @Test
    @DisplayName("Изменение логина пользователя")
    public void testChangeUserEmail() {
        Response response = user.changeUserEmail(accessToken);
        assertEquals(200, response.getStatusCode());
        assertTrue(response.getBody().jsonPath().getBoolean("success"));
    }

    @Test
    @DisplayName("Изменение пароля пользователя")
    public void testChangeUserPassword() {
        Response response = user.changeUserPassword(accessToken);
        assertEquals(200, response.getStatusCode());
        assertTrue(response.getBody().jsonPath().getBoolean("success"));
    }

    @Test
    @DisplayName("Изменение имени пользователя")
    public void testChangeUserName() {
        Response response = user.changeUserName(accessToken);
        assertEquals(200, response.getStatusCode());
        assertTrue(response.getBody().jsonPath().getBoolean("success"));
    }

    @Test
    @DisplayName("Изменение данных пользователя без авторизации")
    public void testChangeUserWithoutAuthorization() {
        Response response = user.changeUserWithoutAuthorization();
        assertEquals(401, response.getStatusCode());
        assertFalse(response.getBody().jsonPath().getBoolean("success"));
    }

    @After
    public void tearDown() {
        if (statusCode == 200) {
            user.deleteUser(accessToken);
        }
    }
}
