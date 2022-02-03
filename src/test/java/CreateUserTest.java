import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateUserTest extends RestAssuredUser {

    private static final String USER_PATH = "api/auth/register/";
    private static final String LOGIN_PATH = "api/auth/login/";

    public String accessToken;

    @Test
    @DisplayName("Creation user with correct data")
    @Description("This is the creation user test with correct data")
    public void createUserWithCorrectData() {
        User user = User.getRandom();
        Response response = createNewUser(user);
        response.then().assertThat().body("success", equalTo(true));
        String accessToken = extractToken(user);
        getAccessToken();
        delete();
    }

    @Test
    @DisplayName("Creation duplicate user")
    @Description("This is the creation duplicate user test")
    public void canNotCreateDuplicateUser() {
        User user = User.getRandom();
        createNewUser(user);
        Response response = createNewUser(user);
        response.then().assertThat().body("message", equalTo("User already exists"))
                .and()
                .statusCode(403);
        String accessToken = extractToken(user);
        getAccessToken();
        delete();
    }

    @Test
    @DisplayName("Creation user without name")
    @Description("This is the creation user without name test")
    public void canNotCreateUserWithoutName() {
        UserWithoutName user = UserWithoutName.getRandom();
        Response response = createNewUserWithoutName(user);
        response.then().assertThat().body("message", equalTo("Email, password and name are required fields"))
                .and()
                .statusCode(403);
    }

    @Test
    @DisplayName("Creation user without password")
    @Description("This is the creation user without password test")
    public void canNotCreateUserWithoutPassword() {
        UserWithoutPassword user = UserWithoutPassword.getRandom();
        Response response = createNewUserWithoutPassword(user);
        response.then().assertThat().body("message", equalTo("Email, password and name are required fields"))
                .and()
                .statusCode(403);
    }

    @Test
    @DisplayName("Creation user without email")
    @Description("This is the creation user without email test")
    public void canNotCreateUserWithoutEmail() {
        UserWithoutEmail user = UserWithoutEmail.getRandom();
        Response response = createNewUserWithoutEmail(user);
        response.then().assertThat().body("message", equalTo("Email, password and name are required fields"))
                .and()
                .statusCode(403);
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

    @Step("Create new user without name")
    public Response createNewUserWithoutName(UserWithoutName user) {
        Response response = given()
                .spec(getBaseSpecification())
                .body(user)
                .when()
                .post(USER_PATH);
        return response;
    }

    @Step("Create new user without password")
    public Response createNewUserWithoutPassword(UserWithoutPassword user) {
        Response response = given()
                .spec(getBaseSpecification())
                .body(user)
                .when()
                .post(USER_PATH);
        return response;
    }

    @Step("Create new user without email")
    public Response createNewUserWithoutEmail(UserWithoutEmail user) {
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
