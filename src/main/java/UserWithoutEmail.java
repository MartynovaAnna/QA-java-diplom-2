import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class UserWithoutEmail {

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
}
