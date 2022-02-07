import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class UserEmail {

    public static final String EMAIL_POSTFIX = "@yandex.ru";

    public final String email;

    public UserEmail(String email) {
        this.email = email;
    }

    @Step("Get new user data")
    public static UserEmail getRandom() {
        final String email = RandomStringUtils.randomAlphabetic(10) + EMAIL_POSTFIX;
        return new UserEmail(email);
    }
}
