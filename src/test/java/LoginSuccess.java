import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LoginSuccess extends BaseTest {
    Faker faker = new Faker();

    @Test
    public void successLoginUser() {
        LoginUserRequest loginUserRequest = new LoginUserRequest("testQA303@gmail.com", "Test123456");
        Response response = postRequest("/api/auth/login", 200, loginUserRequest);
        LoginUserResponse responseBodyLogin = response.as(LoginUserResponse.class);

        assertFalse(responseBodyLogin.getAccessToken().isEmpty());
        assertFalse(responseBodyLogin.getRefreshToken().isEmpty());
        assertNotNull(responseBodyLogin.getExpiration());
    }

    @Test
    public void successLoginAdmin() {
        LoginUserRequest loginUserRequest = new LoginUserRequest("AdminTestQA303@gmail.com", "Test123456");
        Response response = postRequest("/api/auth/login", 200, loginUserRequest);
        LoginUserResponse responseBodyLogin = response.as(LoginUserResponse.class);

        assertFalse(responseBodyLogin.getAccessToken().isEmpty());
        assertFalse(responseBodyLogin.getRefreshToken().isEmpty());
        assertNotNull(responseBodyLogin.getExpiration());
    }

    @Test
    public void invalidSuccessLoginUserEmailEmpty() {
        String email = "";
        String password = faker.internet().password();

        LoginUserRequest loginUserRequest = new LoginUserRequest(email, password);
        Response response = postRequest("/api/auth/login", 400, loginUserRequest);
        Assertions.assertEquals(400, response.getStatusCode());

        String responseBody = response.getBody().asString();
        Assertions.assertTrue(responseBody.contains("Email cannot be empty"));
        Assertions.assertTrue(responseBody.contains("Invalid email format"));
    }
    @Test
    public void invalidSuccessLoginUserPasswordEmpty (){
        String email = faker.internet().emailAddress();
        String password = "";

        LoginUserRequest loginUserRequest = new LoginUserRequest(email, password);
        Response response = postRequest("/api/auth/login", 400, loginUserRequest);
        Assertions.assertEquals(400, response.getStatusCode());

        String responseBody = response.getBody().asString();
        Assertions.assertTrue(responseBody.contains("Password cannot be empty"));
        Assertions.assertTrue(responseBody.contains("Password must contain at least 8 characters"));
        Assertions.assertTrue(responseBody.contains("Password must contain letters and numbers"));

    }

}
