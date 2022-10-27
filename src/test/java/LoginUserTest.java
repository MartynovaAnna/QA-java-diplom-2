import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class LoginUserTest extends RestAssuredUser {

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
    @DisplayName("Login user with correct data")
    @Description("This is the login user test with correct data")
    public void loginWithCorrectData() {
        User user = User.getRandom();
        user.createNewUser(user);
        Response response = user.userLogin(user);
        response.then().assertThat().body("success", equalTo(true));
        String accessToken = user.extractToken(user);
        getAccessToken();
    }

    @Test
    @DisplayName("Login user with wrong email")
    @Description("This is the login user test with wrong email")
    public void loginWithWrongEmail() {
        UserWithWrongEmail user = UserWithWrongEmail.getRandom();
        user.createNewUserWithWrongEmail(user);
        UserWithWrongEmail wrongEmailUser = UserWithWrongEmail.getRandom();
        Response response = user.userWithWrongEmailLogin(wrongEmailUser);
        response.then().assertThat().body("message", equalTo("email or password are incorrect"))
                .and()
                .statusCode(401);
        String accessToken = user.extractTokenWrongEmail(user);
        getAccessToken();
    }

    @Test
    @DisplayName("Login user with wrong password")
    @Description("This is the login user test with wrong password")
    public void loginWithWrongPassword() {
        UserWithWrongPassword user = UserWithWrongPassword.getRandom();
        user.createNewUserWithWrongPassword(user);
        UserWithWrongPassword wrongPasswordUser = UserWithWrongPassword.getRandom();
        Response response = user.userWithWrongPasswordLogin(wrongPasswordUser);
        response.then().assertThat().body("message", equalTo("email or password are incorrect"))
                .and()
                .statusCode(401);
        String accessToken = user.extractTokenWrongPassword(user);
        getAccessToken();
    }
}