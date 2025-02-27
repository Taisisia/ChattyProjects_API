import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreatePostTest extends BaseTest {
    Faker faker = new Faker();


    @Test
    public void createPost() {
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
        assertEquals(postRequest.getTitle(), postResponse.getTitle());
    }

    @Test
    public void invalidCreatePostTitleEmpty() {
        String descriptionRandom = faker.lorem().sentence();
        String bodyRandom = faker.lorem().paragraph();

        LoginUserRequest loginUserRequest = new LoginUserRequest("testQA303@gmail.com", "Test123456");
        Response response = postRequest("/api/auth/login", 200, loginUserRequest);
        LoginUserResponse responseBodyLogin = response.as(LoginUserResponse.class);
        String token = responseBodyLogin.getAccessToken();

        PostRequest postRequest = new PostRequest(
               "",
                descriptionRandom,
                bodyRandom,
                "",
                "",
                false);
        Response createPostResponse = postRequestWithToken("/api/posts", 400, postRequest, token);

        Assertions.assertEquals(400, createPostResponse.getStatusCode());
        String responseBody = createPostResponse.getBody().asString();

        Assertions.assertTrue(responseBody.contains("Title can not be empty!"));
        Assertions.assertTrue(responseBody.contains("Title must contain from 1 to 40 characters"));
    }
    @Test
    public void invalidCreatePostDescriptionEmpty() {
        String titleRandom = faker.lorem().word();
        String bodyRandom = faker.lorem().paragraph();

        LoginUserRequest loginUserRequest = new LoginUserRequest("testQA303@gmail.com", "Test123456");
        Response response = postRequest("/api/auth/login", 200, loginUserRequest);
        LoginUserResponse responseBodyLogin = response.as(LoginUserResponse.class);
        String token = responseBodyLogin.getAccessToken();

        PostRequest postRequest = new PostRequest(
                titleRandom,
                "",
                bodyRandom,
                "",
                "",
                false);
        Response createPostResponse = postRequestWithToken("/api/posts", 400, postRequest, token);

        Assertions.assertEquals(400, createPostResponse.getStatusCode());
        String responseBody = createPostResponse.getBody().asString();

        Assertions.assertTrue(responseBody.contains("Description can not be empty!"));
        Assertions.assertTrue(responseBody.contains("Description must contain from 1 to 100 characters"));
    }
    @Test
    public void createPostBodyEmpty() {
        String titleRandom = faker.lorem().word();
        String descriptionRandom = faker.lorem().sentence();

        LoginUserRequest loginUserRequest = new LoginUserRequest("testQA303@gmail.com", "Test123456");
        Response response = postRequest("/api/auth/login", 200, loginUserRequest);
        LoginUserResponse responseBodyLogin = response.as(LoginUserResponse.class);
        String token = responseBodyLogin.getAccessToken();

        PostRequest postRequest = new PostRequest(
                titleRandom,
                descriptionRandom,
                "",
                "",
                "",
                false);
        Response createPostResponse = postRequestWithToken("/api/posts", 400, postRequest, token);

        Assertions.assertEquals(400, createPostResponse.getStatusCode());
        String responseBody = createPostResponse.getBody().asString();

        Assertions.assertTrue(responseBody.contains("Body can not be empty!"));
        Assertions.assertTrue(responseBody.contains("Body must contain from 1 to 1000 characters"));
    }
}
