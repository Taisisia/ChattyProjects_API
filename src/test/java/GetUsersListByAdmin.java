import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetUsersListByAdmin extends BaseTest {
    @Test
    public void getUserListTest (){
        LoginUserRequest loginUserRequest = new LoginUserRequest("AdminTestQA303@gmail.com", "Test123456");
        Response response = postRequest("/api/auth/login", 200, loginUserRequest);
        LoginUserResponse responseBody = response.as(LoginUserResponse.class);

        String token = responseBody.getAccessToken();
        Response responseGetRequest = getRequest("/api/users?skip=0&limit=3", 200, token);

        JsonPath jsonPath = responseGetRequest.jsonPath();
        List<Map<String, Object>> user = jsonPath.getList("");
        assertEquals(3, user.size());
    }
}
