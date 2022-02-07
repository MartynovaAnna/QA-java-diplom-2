import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class OrderCreationTest extends RestAssuredUser {

    public String accessToken;

    public String getAccessToken() {
        {
            return accessToken;
        }
    }

    @After
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

    @Test
    @DisplayName("Creation order with authorization")
    @Description("This is the creation correct order test with authorization")
    public void correctOrderWithAuthorization() {
        User user = User.getRandom();
        OrderCreation order = new OrderCreation();
        user.createNewUser(user);
        String accessToken = user.extractToken(user);
        getAccessToken();
        Response response = order.orderCreation(accessToken.substring(7));
        response.then().assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Creation incorrect order with authorization")
    @Description("This is the creation incorrect order test with authorization")
    public void incorrectOrderWithAuthorization() {
        User user = User.getRandom();
        OrderCreation order = new OrderCreation();
        user.createNewUser(user);
        String accessToken = user.extractToken(user);
        getAccessToken();
        Response response = order.incorrectOrderCreation(accessToken.substring(7));
        response.then().statusCode(500);
    }

    @Test
    @DisplayName("Creation empty order with authorization")
    @Description("This is the creation incorrect empty order test with authorization")
    public void emptyOrderWithAuthorization() {
        User user = User.getRandom();
        OrderCreation order = new OrderCreation();
        user.createNewUser(user);
        String accessToken = user.extractToken(user);
        getAccessToken();
        Response response = order.emptyOrderCreation(accessToken.substring(7));
        response.then().assertThat().body("message", equalTo("Ingredient ids must be provided"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Creation order without authorization")
    @Description("This is the creation correct order test without authorization")
    public void correctOrderWithoutAuthorization() {
        OrderCreation order = new OrderCreation();
        Response response = order.orderCreationWithoutAuthorization();
        response.then().assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Creation incorrect order without authorization")
    @Description("This is the creation incorrect order test without authorization")
    public void incorrectOrderWithoutAuthorization() {
        OrderCreation order = new OrderCreation();
        Response response = order.incorrectOrderCreationWithoutAuthorization();
        response.then().statusCode(500);
    }

    @Test
    @DisplayName("Creation empty order without authorization")
    @Description("This is the creation incorrect empty order test without authorization")
    public void emptyOrderWithoutAuthorization() {
        OrderCreation order = new OrderCreation();
        Response response = order.emptyOrderCreationWithoutAuthorization();
        response.then().assertThat().body("message", equalTo("Ingredient ids must be provided"))
                .and()
                .statusCode(400);
    }
}
