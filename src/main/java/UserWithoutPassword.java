import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;

import static io.restassured.RestAssured.given;

public class UserWithoutPassword extends RestAssuredUser {

    private static final String USER_PATH = "api/auth/register/";
    public static final String EMAIL_POSTFIX = "@yandex.ru";

    public final String email;
    public final String name;

    public UserWithoutPassword(String email, String name) {
        this.email = email;
        this.name = name;
    }

    @Step("Get new user without password")
    public static UserWithoutPassword getRandom() {
        final String email = RandomStringUtils.randomAlphabetic(10) + EMAIL_POSTFIX;;
        final String name = RandomStringUtils.randomAlphabetic(10);
        return new UserWithoutPassword(email, name);
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
}
