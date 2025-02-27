import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetUser extends BaseTest {
    Faker faker = new Faker();

    @Test
    public void getUserData() {
        LoginUserRequest requestBody = new LoginUserRequest("testQA303@gmail.com", "Test123456");
        Response response = postRequest("/api/auth/login", 200, requestBody);

        LoginUserResponse responseBody = response.as(LoginUserResponse.class);
        String token = responseBody.getAccessToken();

        Response responseSecond = getRequest("/api/me", 200, token);
        UserDataResponse userInfo = responseSecond.as(UserDataResponse.class);
        System.out.println("response" + response);
        System.out.println("responseSecond" + responseSecond);
    }

    @Test
    public void getAdminData() {
        LoginUserRequest requestBody = new LoginUserRequest("AdminTestQA303@gmail.com", "Test123456");
        Response responsePostRequest = postRequest("/api/auth/login", 200, requestBody);

        LoginUserResponse responseBody = responsePostRequest.as(LoginUserResponse.class);
        String token = responseBody.getAccessToken();

        Response responseGetRequest = getRequest("/api/me", 200, token);
        UserDataResponse userInfo = responseGetRequest.as(UserDataResponse.class);
        System.out.println("response" + responsePostRequest);
        System.out.println("responseGetRequest" + responseGetRequest);
    }
}

