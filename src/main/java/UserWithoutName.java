import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;

import static io.restassured.RestAssured.given;

public class UserWithoutName extends RestAssuredUser {

    private static final String USER_PATH = "api/auth/register/";
    public static final String EMAIL_POSTFIX = "@yandex.ru";

    public final String email;
    public final String password;

    public UserWithoutName(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Step("Get new user without name")
    public static UserWithoutName getRandom() {
        final String email = RandomStringUtils.randomAlphabetic(10) + EMAIL_POSTFIX;;
        final String password = RandomStringUtils.randomAlphabetic(10);
        return new UserWithoutName(email, password);
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
}
