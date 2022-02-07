import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;

import static io.restassured.RestAssured.given;

public class User extends RestAssuredUser {

    public static final String EMAIL_POSTFIX = "@yandex.ru";
    private static final String USER_PATH = "api/auth/register/";
    private static final String LOGIN_PATH = "api/auth/login/";
    private static final String CHANGE_DATA_PATH = "api/auth/user/";

    public String accessToken;

    public final String email;
    public final String password;
    public final String name;

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Step("Get new user data")
    public static User getRandom() {
        final String email = RandomStringUtils.randomAlphabetic(10) + EMAIL_POSTFIX;;
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String name = RandomStringUtils.randomAlphabetic(10);
        return new User(email, password, name);
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
}
