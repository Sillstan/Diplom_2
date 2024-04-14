import io.restassured.response.Response;

import java.util.*;

import static io.restassured.RestAssured.given;

public class Order {
    private List<String> ingredientList = new ArrayList<>();
    private String ingredient;
    private Map<String, List<String>> requestBody;

    private Map<String, List<String>> createRequestBody() {
        ingredientList = given()
                .spec(BuildAPI.baseRequestSpec())
                .when()
                .get(BuildAPI.INGREDIENTS_ENDPOINT)
                .then()
                .extract()
                .path("data._id");
        Random random = new Random();
        ingredient = ingredientList.get(random.nextInt(ingredientList.size()));
        requestBody = new HashMap<>();
        List<String> ingredientBody = new ArrayList<>();
        ingredientBody.add(ingredient);
        requestBody.put("ingredients", ingredientBody);
        return requestBody;
    }

    private Map<String, List<String>> createIncorrectRequestBody() {
        ingredient = "invalidIngredient";
        requestBody = new HashMap<>();
        List<String> ingredientBody = new ArrayList<>();
        ingredientBody.add(ingredient);
        requestBody.put("ingredients", ingredientBody);
        return requestBody;
    }


    public Response createOrder(String accessToken) {
        requestBody = createRequestBody();
        return given()
                .spec(BuildAPI.baseRequestSpec())
                .header("Authorization", accessToken)
                .body(requestBody)
                .post(BuildAPI.ORDER_ENDPOINT)
                .thenReturn();
    }

    public Response createOrderWithoutAuthorization() {
        requestBody = createRequestBody();
        return given()
                .spec(BuildAPI.baseRequestSpec())
                .body(requestBody)
                .post(BuildAPI.ORDER_ENDPOINT)
                .thenReturn();
    }

    public Response createOrderWithoutIngredients() {
        return given()
                .spec(BuildAPI.baseRequestSpec())
                .post(BuildAPI.ORDER_ENDPOINT)
                .thenReturn();
    }

    public Response createOrderWithIncorrectIngredient() {
        requestBody = createIncorrectRequestBody();
        return given()
                .spec(BuildAPI.baseRequestSpec())
                .body(requestBody)
                .post(BuildAPI.ORDER_ENDPOINT)
                .thenReturn();
    }

    public Response getOrderWithUserToken(String accessToken) {
        return given()
                .spec(BuildAPI.baseRequestSpec())
                .header("Authorization", accessToken)
                .get(BuildAPI.ORDER_ENDPOINT)
                .thenReturn();
    }

    public Response getOrderWithoutUserToken() {
        return given()
                .spec(BuildAPI.baseRequestSpec())
                .get(BuildAPI.ORDER_ENDPOINT)
                .thenReturn();
    }
}
