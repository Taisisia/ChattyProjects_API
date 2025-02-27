import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GetDraftPostsTest extends BaseTest {
    @Test
    public void getDraftPosts() {
        LoginUserRequest loginUserRequest = new LoginUserRequest("testQA303@gmail.com", "Test123456");
        Response responseLogin = postRequest("/api/auth/login", 200, loginUserRequest);
        LoginUserResponse loginUserResponse = responseLogin.as(LoginUserResponse.class);
        String token = loginUserResponse.getAccessToken();
        Response getDraftResponse = getRequest("/api/posts/drafts", 200, token);
        assertEquals(200, getDraftResponse.getStatusCode()); // Проверка успешного получения черновиков
        List<PostResponse> draftPosts = getDraftResponse.jsonPath().getList("drafts", PostResponse.class);
        assertNotNull(draftPosts);
        assertTrue(draftPosts.isEmpty());
//      //  assertFalse(draftPosts.isEmpty());
//        создать черновик
    }
}
