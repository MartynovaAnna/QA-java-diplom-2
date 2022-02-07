import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.io.File;

import static io.restassured.RestAssured.given;

public class OrderCreation extends RestAssuredUser {

    private static final String ORDER_PATH = "api/orders/";
    File correctJson = new File("src/test/resources/CorrectOrder.json");
    File incorrectJson = new File("src/test/resources/IncorrectOrder.json");

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
}
