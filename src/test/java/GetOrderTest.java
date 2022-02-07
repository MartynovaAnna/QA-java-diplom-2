import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

public class GetOrderTest extends RestAssuredUser {

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
    @DisplayName("Getting order with authorization")
    @Description("This is the getting order test with authorization")
    public void getOrderWithAuthorization() {
        User user = User.getRandom();
        user.createNewUser(user);
        String accessToken = user.extractToken(user);
        getAccessToken();
        OrderCreation order = new OrderCreation();
        order.orderCreation(accessToken.substring(7));
        Response response = order.gettingOrder(accessToken.substring(7));
        response.then().assertThat().body("orders", notNullValue());
    }

    @Test
    @DisplayName("Getting order without authorization")
    @Description("This is the getting order test without authorization")
    public void getOrderWithoutAuthorization() {
        OrderCreation order = new OrderCreation();
        order.orderCreationWithoutAuthorization();
        Response response = order.gettingOrderWithoutAuthorization();
        response.then().assertThat().body("message", equalTo("You should be authorised"))
                .and()
                .statusCode(401);
    }
}
