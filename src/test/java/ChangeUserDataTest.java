import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ChangeUserDataTest extends RestAssuredUser {

    private static final String USER_PATH = "api/auth/register/";
    private static final String LOGIN_PATH = "api/auth/login/";
    private static final String CHANGE_DATA_PATH = "api/auth/user/";

    public String accessToken;

    @Test
    @DisplayName("Changing user password with authorization")
    @Description("This is the changing user password test with authorization")
    public void changeUserPassword() {
        User user = User.getRandom();
        UserPassword password = UserPassword.getRandom();
        createNewUser(user);
        String accessToken = extractToken(user);
        getAccessToken();
        Response response = changeUserPassword(password, accessToken.substring(7));
        response.then().assertThat().body("success", equalTo(true));
        delete();
    }

    @Test
    @DisplayName("Changing user email with authorization")
    @Description("This is the changing user email test with authorization")
    public void changeUserEmail() {
        User user = User.getRandom();
        UserEmail email = UserEmail.getRandom();
        createNewUser(user);
        String accessToken = extractToken(user);
        getAccessToken();
        Response response = changeUserEmail(email, accessToken.substring(7));
        response.then().assertThat().body("success", equalTo(true));
        delete();
    }

    @Test
    @DisplayName("Changing user name with authorization")
    @Description("This is the changing user name test with authorization")
    public void changeUserName() {
        User user = User.getRandom();
        UserName name = UserName.getRandom();
        createNewUser(user);
        String accessToken = extractToken(user);
        getAccessToken();
        Response response = changeUserName(name, accessToken.substring(7));
        response.then().assertThat().body("success", equalTo(true));
        delete();
    }

    @Test
    @DisplayName("Changing user password with authorization")
    @Description("This is the changing user password test with authorization")
    public void changeUserPasswordWithoutAuthorization() {
        User user = User.getRandom();
        UserPassword password = UserPassword.getRandom();
        createNewUser(user);
        Response response = changeUserPasswordWithoutAuthorization(password);
        response.then().assertThat().body("message", equalTo("You should be authorised"))
                .and()
                .statusCode(401);
        delete();
    }

    @Test
    @DisplayName("Changing user email with authorization")
    @Description("This is the changing user email test with authorization")
    public void changeUserEmailWithoutAuthorization() {
        User user = User.getRandom();
        UserEmail email = UserEmail.getRandom();
        createNewUser(user);
        Response response = changeUserEmailWithoutAuthorization(email);
        response.then().assertThat().body("message", equalTo("You should be authorised"))
                .and()
                .statusCode(401);
        delete();
    }

    @Test
    @DisplayName("Changing user name with authorization")
    @Description("This is the changing user name test with authorization")
    public void changeUserNameWithoutAuthorization() {
        User user = User.getRandom();
        UserName name = UserName.getRandom();
        createNewUser(user);
        Response response = changeUserNameWithoutAuthorization(name);
        response.then().assertThat().body("message", equalTo("You should be authorised"))
                .and()
                .statusCode(401);
        delete();
    }

    @Step("Change user password")
    public Response changeUserPassword(UserPassword password, String extractToken) {
        Response response = given()
                .spec(getBaseSpecification())
                .auth().oauth2(extractToken)
                .body(password)
                .when()
                .patch(CHANGE_DATA_PATH);
        return response;
    }

    @Step("Change user email")
    public Response changeUserEmail(UserEmail email, String extractToken) {
        Response response = given()
                .spec(getBaseSpecification())
                .auth().oauth2(extractToken)
                .body(email)
                .when()
                .patch(CHANGE_DATA_PATH);
        return response;
    }

    @Step("Change user name")
    public Response changeUserName(UserName name, String extractToken) {
        Response response = given()
                .spec(getBaseSpecification())
                .auth().oauth2(extractToken)
                .body(name)
                .when()
                .patch(CHANGE_DATA_PATH);
        return response;
    }

    @Step("Change user password without authorization")
    public Response changeUserPasswordWithoutAuthorization(UserPassword password) {
        Response response = given()
                .spec(getBaseSpecification())
                .body(password)
                .when()
                .patch(CHANGE_DATA_PATH);
        return response;
    }

    @Step("Change user email without authorization")
    public Response changeUserEmailWithoutAuthorization(UserEmail email) {
        Response response = given()
                .spec(getBaseSpecification())
                .body(email)
                .when()
                .patch(CHANGE_DATA_PATH);
        return response;
    }

    @Step("Change user name without authorization")
    public Response changeUserNameWithoutAuthorization(UserName name) {
        Response response = given()
                .spec(getBaseSpecification())
                .body(name)
                .when()
                .patch(CHANGE_DATA_PATH);
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

    @Step("User login")
    public Response userLogin(User user) {
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
