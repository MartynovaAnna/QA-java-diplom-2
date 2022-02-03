import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class LoginUserTest extends RestAssuredUser {

    private static final String USER_PATH = "api/auth/register/";
    private static final String LOGIN_PATH = "api/auth/login/";

    public String accessToken;

    @Test
    @DisplayName("Login user with correct data")
    @Description("This is the login user test with correct data")
    public void loginWithCorrectData() {
        User user = User.getRandom();
        createNewUser(user);
        Response response = userLogin(user);
        response.then().assertThat().body("success", equalTo(true));
        String accessToken = extractToken(user);
        getAccessToken();
        delete();
    }

    @Test
    @DisplayName("Login user with wrong email")
    @Description("This is the login user test with wrong email")
    public void loginWithWrongEmail() {
        UserWithWrongEmail user = UserWithWrongEmail.getRandom();
        createNewUserWithWrongEmail(user);
        UserWithWrongEmail wrongEmailUser = UserWithWrongEmail.getRandom();
        Response response = userWithWrongEmailLogin(wrongEmailUser);
        response.then().assertThat().body("message", equalTo("email or password are incorrect"))
                .and()
                .statusCode(401);
        String accessToken = extractTokenWrongEmail(user);
        getAccessToken();
        delete();
    }

    @Test
    @DisplayName("Login user with wrong password")
    @Description("This is the login user test with wrong password")
    public void loginWithWrongPassword() {
        UserWithWrongPassword user = UserWithWrongPassword.getRandom();
        createNewUserWithWrongPassword(user);
        UserWithWrongPassword wrongPasswordUser = UserWithWrongPassword.getRandom();
        Response response = userWithWrongPasswordLogin(wrongPasswordUser);
        response.then().assertThat().body("message", equalTo("email or password are incorrect"))
                .and()
                .statusCode(401);
        String accessToken = extractTokenWrongPassword(user);
        getAccessToken();
        delete();

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

    @Step("Create new user with wrong email")
    public Response createNewUserWithWrongEmail(UserWithWrongEmail user) {
        Response response = given()
                .spec(getBaseSpecification())
                .body(user)
                .when()
                .post(USER_PATH);
        return response;
    }

    @Step("Create new user with wrong password")
    public Response createNewUserWithWrongPassword(UserWithWrongPassword user) {
        Response response = given()
                .spec(getBaseSpecification())
                .body(user)
                .when()
                .post(USER_PATH);
        return response;
    }

    @Step("User login")
    public Response userLogin(User user) {
        Response response = given()
                .spec(getBaseSpecification())
                .body(user)
                .when()
                .post(LOGIN_PATH);
        return response;
    }

    @Step("User with wrong email login")
    public Response userWithWrongEmailLogin(UserWithWrongEmail user) {
        Response response = given()
                .spec(getBaseSpecification())
                .body(user)
                .when()
                .post(LOGIN_PATH);
        return response;
    }

    @Step("User with wrong password login")
    public Response userWithWrongPasswordLogin(UserWithWrongPassword user) {
        Response response = given()
                .spec(getBaseSpecification())
                .body(user)
                .when()
                .post(LOGIN_PATH);
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

    @Step("Extract access token for user with wrong email")
    public String extractTokenWrongEmail(UserWithWrongEmail user) {
        return given()
                .spec(getBaseSpecification())
                .body(user)
                .when()
                .post(LOGIN_PATH)
                .then()
                .extract()
                .path("accessToken");
    }

    @Step("Extract access token for user with wrong password")
    public String extractTokenWrongPassword(UserWithWrongPassword user) {
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
