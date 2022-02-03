import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class UserWithoutName {

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
}
