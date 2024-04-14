import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoginUserTest {
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
    @DisplayName("Логин под существующим пользователем")
    public void testLoginUser() {
        Response response = user.loginUser();
        assertEquals(200, response.getStatusCode());
        assertTrue(response.getBody().jsonPath().getBoolean("success"));
    }

    @Test
    @DisplayName("Логин с фальшивым логином")
    public void testLoginUserWithIncorrectEmail() {
        Response response = user.loginUserWithIncorrectLogin();
        assertEquals(401, response.getStatusCode());
        assertFalse(response.getBody().jsonPath().getBoolean("success"));
    }

    @Test
    @DisplayName("Логин с фальшивым паролем")
    public void testLoginUserWithIncorrectPassword() {
        Response response = user.loginUserWithIncorrectPassword();
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
