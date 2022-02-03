import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class UserWithWrongEmail {

    public static final String EMAIL_POSTFIX = "@yandex.ru";

    public final String email;
    public final String password;
    public final String name;

    public UserWithWrongEmail(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Step("Get new user data")
    public static UserWithWrongEmail getRandom() {
        final String email = RandomStringUtils.randomAlphabetic(10) + EMAIL_POSTFIX;;
        final String password = "Dfwe3433ff";
        final String name = "Naruto";
        return new UserWithWrongEmail(email, password, name);
    }
}
