import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class UpdatePasswordTest extends BaseTest {
    Faker faker = new Faker();
    @Test
    public void updatePassword() {
        String name= faker.name().name();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        String passwordNew = faker.internet().password();

        CreateUserRequest createUserRequest = new CreateUserRequest(name, email, password, password, "admin");
        Response response = postRequest("/api/auth/register", 201, createUserRequest);
        CreateUserResponse createUserResponseBody = response.as(CreateUserResponse.class);
        LoginUserRequest loginUserRequest = new LoginUserRequest(email, password);
        Response responsePostRequestLogin = postRequest("/api/auth/login", 200, loginUserRequest);
        LoginUserResponse responseBodyLogin = responsePostRequestLogin.as(LoginUserResponse.class);
        String token = responseBodyLogin.getAccessToken();
        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest(password, passwordNew, passwordNew);
        Response updatePasswordResponse = putRequest("/api/user/password/update", 200, updatePasswordRequest, token);
    }
    @Test
    public void invalidUpdatePasswordNewPasswordEmpty() {
        String name= faker.name().firstName();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        String passwordNew = "";

        CreateUserRequest createUserRequest = new CreateUserRequest(name, email, password, password, "admin");
        Response response = postRequest("/api/auth/register", 201, createUserRequest);
        CreateUserResponse createUserResponseBody = response.as(CreateUserResponse.class);
        LoginUserRequest loginUserRequest = new LoginUserRequest(email, password);
        Response responsePostRequestLogin = postRequest("/api/auth/login", 200, loginUserRequest);
        LoginUserResponse responseBodyLogin = responsePostRequestLogin.as(LoginUserResponse.class);
        String token = responseBodyLogin.getAccessToken();
        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest(password, passwordNew, passwordNew);
        Response updatePasswordResponse = putRequest("/api/user/password/update", 400, updatePasswordRequest, token);
        assertEquals(400, updatePasswordResponse.getStatusCode());
    }

    @Test
    public void invalidUpdatePasswordEmpty() {
        String passwordNew = faker.internet().password();

        LoginUserRequest loginUserRequest = new LoginUserRequest("testQA303@gmail.com", "Test123456");
        Response responsePostRequestLogin = postRequest("/api/auth/login", 200, loginUserRequest);
        LoginUserResponse responseBodyLogin = responsePostRequestLogin.as(LoginUserResponse.class);
        String token = responseBodyLogin.getAccessToken();
        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest("", passwordNew, passwordNew);
        Response updatePasswordResponse = putRequest("/api/user/password/update", 400, updatePasswordRequest, token);
        assertEquals(400, updatePasswordResponse.getStatusCode());
    }
}
