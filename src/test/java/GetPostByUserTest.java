import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class GetPostByUserTest extends BaseTest{
    @Test
    public void getPostByUser(){
        LoginUserRequest loginUserRequest = new LoginUserRequest("testQA303@gmail.com", "Test123456");
        Response responseLogin = postRequest("/api/auth/login", 200, loginUserRequest);
        LoginUserResponse loginUserResponse = responseLogin.as(LoginUserResponse.class);
        String token = loginUserResponse.getAccessToken();

        Response response = getRequest("/api/me", 200, token);
        UserDataResponse userInfo = response.as(UserDataResponse.class);
        String userId = userInfo.getId();

        Response getUsersPost = getRequest("/api/users/" + userId + "/posts?skip=0&limit=10", 200, token);
    }
}
