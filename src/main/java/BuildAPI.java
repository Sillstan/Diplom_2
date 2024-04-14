import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class BuildAPI {
    public static final String URL = "https://stellarburgers.nomoreparties.site/";
    public static final String CREATE_USER_ENDPOINT = "api/auth/register"; // POST
    public static final String CHANGE_USER_ENDPOINT = "api/auth/user";
    public static final String LOGIN_USER_ENDPOINT = "api/auth/login";
    public static final String ORDER_ENDPOINT = "api/orders";
    public static final String INGREDIENTS_ENDPOINT = "api/ingredients";

    protected static RequestSpecification baseRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(URL)
                .setContentType(ContentType.JSON)
                .setRelaxedHTTPSValidation()
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new ErrorLoggingFilter())
                .build();
    }
}
