import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class User {

    public static final String EMAIL_POSTFIX = "@yandex.ru";

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
}
