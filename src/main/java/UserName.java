import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class UserName {

    public final String name;

    public UserName(String name) {
        this.name = name;
    }

    @Step("Get new user data")
    public static UserName getRandom() {
        final String name = RandomStringUtils.randomAlphabetic(10);
        return new UserName(name);
    }
}
