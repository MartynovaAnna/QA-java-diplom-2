import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;

import static io.restassured.RestAssured.given;

public class UserWithoutEmail extends RestAssuredUser {

    private static final String USER_PATH = "api/auth/register/";

    public final String password;
    public final String name;

    public UserWithoutEmail(String password, String name) {
        this.password = password;
        this.name = name;
    }

    @Step("Get new user without email")
    public static UserWithoutEmail getRandom() {
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String name = RandomStringUtils.randomAlphabetic(10);
        return new UserWithoutEmail(password, name);
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
}
