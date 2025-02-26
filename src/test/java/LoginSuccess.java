import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class LoginSuccess extends BaseTest {

    @Test
    public void successLoginUser (){
        LoginUserRequest loginUserRequest = new LoginUserRequest("testQA303@gmail.com", "Test123456");
        Response response = postRequest("/api/auth/login", 200, loginUserRequest);
        LoginUserResponse responseBodyLogin = response.as(LoginUserResponse.class);

        assertFalse(responseBodyLogin.getAccessToken().isEmpty());
        assertFalse(responseBodyLogin.getRefreshToken().isEmpty());
        assertNotNull(responseBodyLogin.getExpiration());
    }
    @Test
    public void successLoginAdmin (){
        LoginUserRequest loginUserRequest = new LoginUserRequest("AdminTestQA303@gmail.com", "Test123456");
        Response response = postRequest("/api/auth/login", 200, loginUserRequest);
        LoginUserResponse responseBodyLogin = response.as(LoginUserResponse.class);

        assertFalse(responseBodyLogin.getAccessToken().isEmpty());
        assertFalse(responseBodyLogin.getRefreshToken().isEmpty());
        assertNotNull(responseBodyLogin.getExpiration());
    }

}
