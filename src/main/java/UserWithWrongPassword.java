import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;

import static io.restassured.RestAssured.given;

public class UserWithWrongPassword extends RestAssuredUser {

    private static final String USER_PATH = "api/auth/register/";
    private static final String LOGIN_PATH = "api/auth/login/";
    public final String email;
    public final String password;
    public final String name;
    public String accessToken;

    public UserWithWrongPassword(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Step("Get new user data")
    public static UserWithWrongPassword getRandom() {
        final String email = "tttttttttest@yandex.ru";
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String name = "Naruto";
        return new UserWithWrongPassword(email, password, name);
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

    @Step("User with wrong password login")
    public Response userWithWrongPasswordLogin(UserWithWrongPassword user) {
        Response response = given()
                .spec(getBaseSpecification())
                .body(user)
                .when()
                .post(LOGIN_PATH);
        return response;
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
}
