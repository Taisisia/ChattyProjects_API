import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CreateUserTest extends BaseTest {

    Faker faker = new Faker();

    @Test
    public void successCreatedUserRandom() {
        String name= faker.name().firstName();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();


        CreateUserRequest createUserRequest = new CreateUserRequest(name,email ,password , password, "user");
        Response response = postRequest("/api/auth/register", 201, createUserRequest);

        CreateUserResponse createUserResponseBody = response.as(CreateUserResponse.class);
        assertFalse(createUserResponseBody.getAccessToken().isEmpty());
        assertFalse(createUserResponseBody.getRefreshToken().isEmpty());
        assertNotNull(createUserResponseBody.getExpiration());

    }
    @Test
    public void successCreatedAdminRandom() {
        String name= faker.name().firstName();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();

        CreateUserRequest createUserRequest = new CreateUserRequest(name, email, password, password, "admin");
        Response response = postRequest("/api/auth/register", 201, createUserRequest);

        CreateUserResponse createUserResponseBody = response.as(CreateUserResponse.class);

        //Check that Access token, refresh token and expiration are not empty
        assertFalse(createUserResponseBody.getAccessToken().isEmpty());
        assertFalse(createUserResponseBody.getRefreshToken().isEmpty());
        assertNotNull(createUserResponseBody.getExpiration());

    }

    @Test
    public void successCreatedUser() {
        CreateUserRequest createUserRequest = new CreateUserRequest("John", "testQA303@gmail.com", "Test123456", "Test123456", "user");
        Response response = postRequest("/api/auth/register", 201, createUserRequest);

        CreateUserResponse createUserResponseBody = response.as(CreateUserResponse.class);
        assertFalse(createUserResponseBody.getAccessToken().isEmpty());
        assertFalse(createUserResponseBody.getRefreshToken().isEmpty());
        assertNotNull(createUserResponseBody.getExpiration());

    }

    @Test
    public void successCreatedAdmin() {
        CreateUserRequest createUserRequest = new CreateUserRequest("JohnAdmin", "AdminTestQA303@gmail.com", "Test123456", "Test123456", "admin");
        Response response = postRequest("/api/auth/register", 201, createUserRequest);

        CreateUserResponse createUserResponseBody = response.as(CreateUserResponse.class);

        //Check that Access token, refresh token and expiration are not empty
        assertFalse(createUserResponseBody.getAccessToken().isEmpty());
        assertFalse(createUserResponseBody.getRefreshToken().isEmpty());
        assertNotNull(createUserResponseBody.getExpiration());

    }

}

