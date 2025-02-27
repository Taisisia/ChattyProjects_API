import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetPostById extends BaseTest {
    Faker faker = new Faker();

    @Test
    public void getPostsByID() {
        String titleRandom = faker.lorem().word();
        String descriptionRandom = faker.lorem().sentence();
        String bodyRandom = faker.lorem().paragraph();

        LoginUserRequest loginUserRequest = new LoginUserRequest("testQA303@gmail.com", "Test123456");
        Response responseLogin = postRequest("/api/auth/login", 200, loginUserRequest);
        LoginUserResponse loginUserResponse = responseLogin.as(LoginUserResponse.class);
        String token = loginUserResponse.getAccessToken();

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

        Response getPostByIdResponse = getRequest("/api/posts/" + postId, 200, token);
        assertEquals(200, getPostByIdResponse.getStatusCode());
    }
    @Test
    public void getPostByInvalidID() {
        UUID invalidPostId = UUID.randomUUID();

        LoginUserRequest loginUserRequest = new LoginUserRequest("testQA303@gmail.com", "Test123456");
        Response responseLogin = postRequest("/api/auth/login", 200, loginUserRequest);
        LoginUserResponse loginUserResponse = responseLogin.as(LoginUserResponse.class);
        String token = loginUserResponse.getAccessToken();

        Response getPostByIdResponse = getRequest("/api/posts/" + invalidPostId, 404, token);
        assertEquals(404, getPostByIdResponse.getStatusCode(), "Post not found!");
    }
}
