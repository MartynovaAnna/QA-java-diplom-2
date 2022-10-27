import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateUserTest extends RestAssuredUser {

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
    @DisplayName("Creation user with correct data")
    @Description("This is the creation user test with correct data")
    public void createUserWithCorrectData() {
        User user = User.getRandom();
        Response response = user.createNewUser(user);
        response.then().assertThat().body("success", equalTo(true));
        String accessToken = user.extractToken(user);
        getAccessToken();
    }

    @Test
    @DisplayName("Creation duplicate user")
    @Description("This is the creation duplicate user test")
    public void canNotCreateDuplicateUser() {
        User user = User.getRandom();
        user.createNewUser(user);
        Response response = user.createNewUser(user);
        response.then().assertThat().body("message", equalTo("User already exists"))
                .and()
                .statusCode(403);
        String accessToken = user.extractToken(user);
        getAccessToken();
    }

    @Test
    @DisplayName("Creation user without name")
    @Description("This is the creation user without name test")
    public void canNotCreateUserWithoutName() {
        UserWithoutName user = UserWithoutName.getRandom();
        Response response = user.createNewUserWithoutName(user);
        response.then().assertThat().body("message", equalTo("Email, password and name are required fields"))
                .and()
                .statusCode(403);
    }

    @Test
    @DisplayName("Creation user without password")
    @Description("This is the creation user without password test")
    public void canNotCreateUserWithoutPassword() {
        UserWithoutPassword user = UserWithoutPassword.getRandom();
        Response response = user.createNewUserWithoutPassword(user);
        response.then().assertThat().body("message", equalTo("Email, password and name are required fields"))
                .and()
                .statusCode(403);
    }

    @Test
    @DisplayName("Creation user without email")
    @Description("This is the creation user without email test")
    public void canNotCreateUserWithoutEmail() {
        UserWithoutEmail user = UserWithoutEmail.getRandom();
        Response response = user.createNewUserWithoutEmail(user);
        response.then().assertThat().body("message", equalTo("Email, password and name are required fields"))
                .and()
                .statusCode(403);
    }
}
