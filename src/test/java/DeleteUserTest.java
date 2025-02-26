import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class DeleteUserTest extends BaseTest {
    Faker faker = new Faker();
    @Test
    public void successDeleteUser() {
        String name= faker.name().firstName();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();

        CreateUserRequest createUserRequest = new CreateUserRequest(name,email ,password , password, "user");
        Response response = postRequest("/api/auth/register", 201, createUserRequest);
        CreateUserResponse responseBody = response.as(CreateUserResponse.class);
        String token = responseBody.getAccessToken();

        Response responseUser = getRequest("/api/me", 200, token);
        UserDataResponse userInfo = responseUser.as(UserDataResponse.class);
        String userId = userInfo.getId();

        System.out.println("user Id = " + userId);

        LoginUserRequest loginAdminRequest = new LoginUserRequest("AdminTestQA303@gmail.com", "Test123456");
        Response response1 = postRequest("/api/auth/login", 200, loginAdminRequest);
        LoginUserResponse responseAdmin = response1.as(LoginUserResponse.class);

        String tokenAdmin = responseAdmin.getAccessToken();
        System.out.println("Admin Token: " + tokenAdmin);

        deleteRequestAsAdmin("/api/users/" + userId, 204, tokenAdmin);

    }
}
