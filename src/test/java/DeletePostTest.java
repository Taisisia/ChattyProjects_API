
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeletePostTest extends BaseTest {
    Faker faker = new Faker();

    @Test
    public void deletePost() {
        String titleRandom = faker.lorem().word();
        String descriptionRandom = faker.lorem().sentence();
        String bodyRandom = faker.lorem().paragraph();

        LoginUserRequest loginUserRequest = new LoginUserRequest("testQA303@gmail.com", "Test123456");
        Response response = postRequest("/api/auth/login", 200, loginUserRequest);
        LoginUserResponse responseBodyLogin = response.as(LoginUserResponse.class);
        String token = responseBodyLogin.getAccessToken();

        PostRequest postRequest = new PostRequest(
                titleRandom,
                descriptionRandom,
                bodyRandom,
                "",
                "",
                false);

        Response createPostResponse = postRequestWithToken("/api/posts", 201, postRequest, token);

        PostResponse postResponse = createPostResponse.as(PostResponse.class);
        String postId = postResponse.getId();
        Response deletePostResponse = deleteRequest("/api/posts/" + postId, 204, token);

        assertEquals(204, deletePostResponse.statusCode());
    }

    @Test
    public void deleteNonExistentPost() {

        LoginUserRequest loginUserRequest = new LoginUserRequest("testQA303@gmail.com", "Test123456");
        Response response = postRequest("/api/auth/login", 200, loginUserRequest);
        LoginUserResponse responseBodyLogin = response.as(LoginUserResponse.class);
        String token = responseBodyLogin.getAccessToken();

        String nonExistentPostId = UUID.randomUUID().toString();

        Response deletePostResponse = deleteRequest("/api/posts/" + nonExistentPostId, 404, token);

        assertEquals(404, deletePostResponse.statusCode());
    }
}
