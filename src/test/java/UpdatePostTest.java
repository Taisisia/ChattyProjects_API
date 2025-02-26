import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class UpdatePostTest extends BaseTest {

       Faker faker = new Faker();



    @Test
    public void updatePost() {
        String titleRandom = faker.lorem().word();
        String descriptionRandom = faker.lorem().sentence();
        String bodyRandom = faker.lorem().paragraph();
        String titleRandomUpdate = faker.lorem().word();
        String descriptionRandomUpdate = faker.lorem().sentence();
        String bodyRandomUpdate = faker.lorem().paragraph();

        LoginUserRequest loginUserRequest = new LoginUserRequest("testQA303@gmail.com", "Test123456");
        Response response = BaseTest.postRequest("/api/auth/login", 200, loginUserRequest);
        LoginUserResponse responseBodyLogin = response.as(LoginUserResponse.class);
        String token = responseBodyLogin.getAccessToken();

        PostRequest postRequest = new PostRequest(
                titleRandom,
                descriptionRandom ,
                bodyRandom,
                "",
                "",
                false);
        Response createPostResponse = postRequestWithToken("/api/posts", 201, postRequest, token);

        PostResponse postResponse = createPostResponse.as(PostResponse.class);
        String postId = postResponse.getId();

        PostRequest updatePost = new PostRequest(
                titleRandomUpdate,
                descriptionRandomUpdate,
                bodyRandomUpdate,
                "",
                "",
                false);

        Response updatePostResponse = putRequest("/api/posts/" + postId,200, updatePost, token);

    }
}
