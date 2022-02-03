import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

public class GetOrderTest extends RestAssuredUser {

    private static final String USER_PATH = "api/auth/register/";
    private static final String LOGIN_PATH = "api/auth/login/";
    private static final String ORDER_PATH = "api/orders/";

    public String accessToken;

    File correctJson = new File("src/test/resources/CorrectOrder.json");

    @Test
    @DisplayName("Getting order with authorization")
    @Description("This is the getting order test with authorization")
    public void getOrderWithAuthorization() {
        User user = User.getRandom();
        createNewUser(user);
        String accessToken = extractToken(user);
        getAccessToken();
        orderCreation(accessToken.substring(7));
        Response response = gettingOrder(accessToken.substring(7));
        response.then().assertThat().body("orders", notNullValue());
        delete();
    }

    @Test
    @DisplayName("Getting order without authorization")
    @Description("This is the getting order test without authorization")
    public void getOrderWithoutAuthorization() {
        orderCreationWithoutAuthorization();
        Response response = gettingOrderWithoutAuthorization();
        response.then().assertThat().body("message", equalTo("You should be authorised"))
                .and()
                .statusCode(401);
    }


    @Step("Create new user")
    public Response createNewUser(User user) {
        Response response = given()
                .spec(getBaseSpecification())
                .body(user)
                .when()
                .post(USER_PATH);
        return response;
    }

    @Step("Extract access token")
    public String extractToken(User user) {
        return given()
                .spec(getBaseSpecification())
                .body(user)
                .when()
                .post(LOGIN_PATH)
                .then()
                .extract()
                .path("accessToken");
    }

    @Step("Set access token")
    public String getAccessToken() {
        {
            return accessToken;
        }
    }

    @Step("Delete user")
    public void delete() {
        if (getAccessToken() == null) {
            return;
        }
        given()
                .spec(getBaseSpecification())
                .auth().oauth2(getAccessToken().substring(7))
                .when()
                .delete("auth/user")
                .then()
                .statusCode(202);
    }

    @Step("Create new order with authorization")
    public Response orderCreation(String extractToken) {
        Response response = given()
                .spec(getBaseSpecification())
                .auth().oauth2(extractToken)
                .body(correctJson)
                .when()
                .post(ORDER_PATH);
        return response;
    }

    @Step("Create new order without authorization")
    public Response orderCreationWithoutAuthorization() {
        Response response = given()
                .spec(getBaseSpecification())
                .body(correctJson)
                .when()
                .post(ORDER_PATH);
        return response;
    }

    @Step("Get order with authorization")
    public Response gettingOrder(String extractToken) {
        Response response = given()
                .spec(getBaseSpecification())
                .auth().oauth2(extractToken)
                .when()
                .get(ORDER_PATH);
        return response;
    }

    @Step("Get order without authorization")
    public Response gettingOrderWithoutAuthorization() {
        Response response = given()
                .spec(getBaseSpecification())
                .when()
                .get(ORDER_PATH);
        return response;
    }
}
