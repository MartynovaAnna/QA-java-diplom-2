import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class UserWithoutPassword {

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
}
