import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class UserWithWrongPassword {

    public final String email;
    public final String password;
    public final String name;

    public UserWithWrongPassword(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Step("Get new user data")
    public static UserWithWrongPassword getRandom() {
        final String email = "tttttttttest@yandex.ru";
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String name = "Naruto";
        return new UserWithWrongPassword(email, password, name);
    }
}
