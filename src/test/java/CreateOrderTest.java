import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreateOrderTest {
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
    @DisplayName("Создание заказа с авторизацией и ингредиентами")
    public void testCreateOrder() {
        Response response = order.createOrder(accessToken);
        assertEquals(200, response.getStatusCode());
        assertTrue(response.getBody().jsonPath().getBoolean("success"));
    }

    @Test
    @DisplayName("Создание заказа без авторизации и ингредиентами")
    public void testCreateOrderWithoutAuthorization() {
        Response response = order.createOrderWithoutAuthorization();
        assertEquals(200, response.getStatusCode());
        assertTrue(response.getBody().jsonPath().getBoolean("success"));
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    public void testCreateOrderWithoutIngredients() {
        Response response = order.createOrderWithoutIngredients();
        assertEquals(400, response.getStatusCode());
        assertFalse(response.getBody().jsonPath().getBoolean("success"));
    }

    @Test
    @DisplayName("Создание заказа с невалидными ингредиентами")
    public void testCreatOrderWithIncorrectIngredient() {
        Response response = order.createOrderWithIncorrectIngredient();
        assertEquals(500, response.getStatusCode());
    }

    @After
    public void tearDown() {
        if (statusCode == 200) {
            user.deleteUser(accessToken);
        }
    }
}
