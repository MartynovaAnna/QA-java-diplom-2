import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class OrderCreationTest extends RestAssuredUser {

    private static final String USER_PATH = "api/auth/register/";
    private static final String LOGIN_PATH = "api/auth/login/";
    private static final String ORDER_PATH = "api/orders/";

    File correctJson = new File("src/test/resources/CorrectOrder.json");
    File incorrectJson = new File("src/test/resources/IncorrectOrder.json");


    public String accessToken;

    @Test
    @DisplayName("Creation order with authorization")
    @Description("This is the creation correct order test with authorization")
    public void correctOrderWithAuthorization() {
        User user = User.getRandom();
        createNewUser(user);
        String accessToken = extractToken(user);
        getAccessToken();
        Response response = orderCreation(accessToken.substring(7));
        response.then().assertThat().body("success", equalTo(true));
        delete();
    }

    @Test
    @DisplayName("Creation incorrect order with authorization")
    @Description("This is the creation incorrect order test with authorization")
    public void incorrectOrderWithAuthorization() {
        User user = User.getRandom();
        createNewUser(user);
        String accessToken = extractToken(user);
        getAccessToken();
        Response response = incorrectOrderCreation(accessToken.substring(7));
        response.then().statusCode(500);
        delete();
    }

    @Test
    @DisplayName("Creation empty order with authorization")
    @Description("This is the creation incorrect empty order test with authorization")
    public void emptyOrderWithAuthorization() {
        User user = User.getRandom();
        createNewUser(user);
        String accessToken = extractToken(user);
        getAccessToken();
        Response response = emptyOrderCreation(accessToken.substring(7));
        response.then().assertThat().body("message", equalTo("Ingredient ids must be provided"))
                .and()
                .statusCode(400);
        delete();
    }

    @Test
    @DisplayName("Creation order without authorization")
    @Description("This is the creation correct order test without authorization")
    public void correctOrderWithoutAuthorization() {
        Response response = orderCreationWithoutAuthorization();
        response.then().assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Creation incorrect order without authorization")
    @Description("This is the creation incorrect order test without authorization")
    public void incorrectOrderWithoutAuthorization() {
        Response response = incorrectOrderCreationWithoutAuthorization();
        response.then().statusCode(500);
    }

    @Test
    @DisplayName("Creation empty order without authorization")
    @Description("This is the creation incorrect empty order test without authorization")
    public void emptyOrderWithoutAuthorization() {
        Response response = emptyOrderCreationWithoutAuthorization();
        response.then().assertThat().body("message", equalTo("Ingredient ids must be provided"))
                .and()
                .statusCode(400);
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

    @Step("Create incorrect order with authorization")
    public Response incorrectOrderCreation(String extractToken) {
        Response response = given()
                .spec(getBaseSpecification())
                .auth().oauth2(extractToken)
                .body(incorrectJson)
                .when()
                .post(ORDER_PATH);
        return response;
    }

    @Step("Create empty order with authorization")
    public Response emptyOrderCreation(String extractToken) {
        Response response = given()
                .spec(getBaseSpecification())
                .auth().oauth2(extractToken)
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

    @Step("Create incorrect order without authorization")
    public Response incorrectOrderCreationWithoutAuthorization() {
        Response response = given()
                .spec(getBaseSpecification())
                .body(incorrectJson)
                .when()
                .post(ORDER_PATH);
        return response;
    }

    @Step("Create empty order without authorization")
    public Response emptyOrderCreationWithoutAuthorization() {
        Response response = given()
                .spec(getBaseSpecification())
                .when()
                .post(ORDER_PATH);
        return response;
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
}
