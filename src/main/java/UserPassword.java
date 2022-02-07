import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class UserPassword {

    public final String password;

    public UserPassword(String password) {
        this.password = password;
    }

    @Step("Get new user data")
    public static UserPassword getRandom() {
        final String password = RandomStringUtils.randomAlphabetic(10);
        return new UserPassword(password);
    }
}
