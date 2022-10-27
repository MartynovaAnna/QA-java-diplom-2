import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;

import static io.restassured.RestAssured.given;

public class UserWithWrongEmail extends RestAssuredUser {

    public static final String EMAIL_POSTFIX = "@yandex.ru";
    private static final String USER_PATH = "api/auth/register/";
    private static final String LOGIN_PATH = "api/auth/login/";
    public final String email;
    public final String password;
    public final String name;
    public String accessToken;

    public UserWithWrongEmail(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Step("Get new user data")
    public static UserWithWrongEmail getRandom() {
        final String email = RandomStringUtils.randomAlphabetic(10) + EMAIL_POSTFIX;
        final String password = "Dfwe3433ff";
        final String name = "Naruto";
        return new UserWithWrongEmail(email, password, name);
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

    @Step("User with wrong email login")
    public Response userWithWrongEmailLogin(UserWithWrongEmail user) {
        Response response = given()
                .spec(getBaseSpecification())
                .body(user)
                .when()
                .post(LOGIN_PATH);
        return response;
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
}
