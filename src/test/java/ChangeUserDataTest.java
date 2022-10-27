import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ChangeUserDataTest extends RestAssuredUser {

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
    @DisplayName("Changing user password with authorization")
    @Description("This is the changing user password test with authorization")
    public void changeUserPassword() {
        User user = User.getRandom();
        UserPassword password = UserPassword.getRandom();
        user.createNewUser(user);
        String accessToken = user.extractToken(user);
        getAccessToken();
        Response response = user.changeUserPassword(password, accessToken.substring(7));
        response.then().assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Changing user email with authorization")
    @Description("This is the changing user email test with authorization")
    public void changeUserEmail() {
        User user = User.getRandom();
        UserEmail email = UserEmail.getRandom();
        user.createNewUser(user);
        String accessToken = user.extractToken(user);
        getAccessToken();
        Response response = user.changeUserEmail(email, accessToken.substring(7));
        response.then().assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Changing user name with authorization")
    @Description("This is the changing user name test with authorization")
    public void changeUserName() {
        User user = User.getRandom();
        UserName name = UserName.getRandom();
        user.createNewUser(user);
        String accessToken = user.extractToken(user);
        getAccessToken();
        Response response = user.changeUserName(name, accessToken.substring(7));
        response.then().assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Changing user password with authorization")
    @Description("This is the changing user password test with authorization")
    public void changeUserPasswordWithoutAuthorization() {
        User user = User.getRandom();
        UserPassword password = UserPassword.getRandom();
        user.createNewUser(user);
        Response response = user.changeUserPasswordWithoutAuthorization(password);
        response.then().assertThat().body("message", equalTo("You should be authorised"))
                .and()
                .statusCode(401);
    }

    @Test
    @DisplayName("Changing user email with authorization")
    @Description("This is the changing user email test with authorization")
    public void changeUserEmailWithoutAuthorization() {
        User user = User.getRandom();
        UserEmail email = UserEmail.getRandom();
        user.createNewUser(user);
        Response response = user.changeUserEmailWithoutAuthorization(email);
        response.then().assertThat().body("message", equalTo("You should be authorised"))
                .and()
                .statusCode(401);
    }

    @Test
    @DisplayName("Changing user name with authorization")
    @Description("This is the changing user name test with authorization")
    public void changeUserNameWithoutAuthorization() {
        User user = User.getRandom();
        UserName name = UserName.getRandom();
        user.createNewUser(user);
        Response response = user.changeUserNameWithoutAuthorization(name);
        response.then().assertThat().body("message", equalTo("You should be authorised"))
                .and()
                .statusCode(401);
    }
}
