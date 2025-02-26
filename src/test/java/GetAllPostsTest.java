import com.fasterxml.jackson.databind.ser.Serializers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetAllPostsTest extends BaseTest {
    @Test
    public void getAllPosts() {
        LoginUserRequest loginUserRequest = new LoginUserRequest("testQA303@gmail.com", "Test123456");
        Response responseLogin = postRequest("/api/auth/login", 200, loginUserRequest);
        LoginUserResponse loginUserResponse = responseLogin.as(LoginUserResponse.class);
        String token = loginUserResponse.getAccessToken();

        int postsNumber = 5;
        Response allPostsResponse = getRequest("/api/posts?skip=0&limit=" + postsNumber, 200, token);
        List<PostResponse> getUsersPostResponse = allPostsResponse.jsonPath().getList("posts", PostResponse.class);

        //Check that list is not empty
        assertFalse(getUsersPostResponse.isEmpty(), "The post list is empty!");

        // Check that the number of posts <= postsNumber
        assertTrue(getUsersPostResponse.size() <= postsNumber);
    }
}
