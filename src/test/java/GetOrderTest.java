import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GetOrderTest {
    private Order order;
    private User user;
    private int statusCode;
    private String accessToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = BuildAPI.URL;
        user = new User();
        order = new Order();
        Response response = user.createRandomUser();
        statusCode = response.getStatusCode();
        accessToken = response.jsonPath().getString("accessToken");
    }

    @Test
    @DisplayName("Получение данных заказов пользователя с авторизацией")
    public void testGetOrdersWithUserToken() {
        Response response = order.getOrderWithUserToken(accessToken);
        assertEquals(200, response.getStatusCode());
        assertTrue(response.getBody().jsonPath().getBoolean("success"));
    }

    @Test
    @DisplayName("Получение данных заказов пользователя без авторизации")
    public void testGetOrderWithoutUserToken() {
        Response response = order.getOrderWithoutUserToken();
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
