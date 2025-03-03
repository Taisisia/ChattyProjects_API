import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CreateUserTest extends BaseTest {

    Faker faker = new Faker();

    @Test
    public void successCreatedUserRandom() {
        String name = faker.name().firstName();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();

        CreateUserRequest createUserRequest = new CreateUserRequest(name, email, password, password, "user");
        Response response = postRequest("/api/auth/register", 201, createUserRequest);

        CreateUserResponse createUserResponseBody = response.as(CreateUserResponse.class);
        assertFalse(createUserResponseBody.getAccessToken().isEmpty());
        assertFalse(createUserResponseBody.getRefreshToken().isEmpty());
        assertNotNull(createUserResponseBody.getExpiration());
    }

    @Test
    public void successCreatedAdminRandom() {
        String name = faker.name().firstName();
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

        assertFalse(createUserResponseBody.getAccessToken().isEmpty());
        assertFalse(createUserResponseBody.getRefreshToken().isEmpty());
        assertNotNull(createUserResponseBody.getExpiration());

    }

    @Test
    public void createUserWithEmptyNameField() {
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        CreateUserRequest createUserRequest = new CreateUserRequest("", email, password, password, "user");
        Response response = postRequest("/api/auth/register", 400, createUserRequest);

        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains("CONFLICT"));
        assertTrue(responseBody.contains("Email already exists!"));
    }

    @Test
    public void createUserWithEmptyEmailField() {
        String name = faker.name().firstName();
        String password = faker.internet().password();
        CreateUserRequest createUserRequest = new CreateUserRequest(name, "", password, password, "user");
        Response response = postRequest("/api/auth/register", 400, createUserRequest);

        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains("Email is not valid."));
        assertTrue(responseBody.contains("Email cannot be empty"));
    }

    @Test
    public void createUserWithEmptyPassword() {
        String name = faker.name().firstName();
        String email = faker.internet().emailAddress();
        CreateUserRequest createUserRequest = new CreateUserRequest(name, email, "", "", "user");
        Response response = postRequest("/api/auth/register", 400, createUserRequest);

        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains("Password must contain at least 8 characters"));
        assertTrue(responseBody.contains("Password cannot be empty"));
        assertTrue(responseBody.contains("Password must contain letters and numbers"));
    }

    @Test
    public void createUserWithEmptyConfirmPassword() {
        String name = faker.name().firstName();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();

        CreateUserRequest createUserRequest = new CreateUserRequest(name, email, password, "", "user");
        Response response = postRequest("/api/auth/register", 400, createUserRequest);
        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains("You need to confirm your password"));
    }

    @Test
    public void createUserWithEmptyRole() {
        String name = faker.name().firstName();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        CreateUserRequest createUserRequest = new CreateUserRequest(name, email, password, password, "");
        Response response = postRequest("/api/auth/register", 400, createUserRequest);
        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains("Role must be either 'admin' or 'user'"));
    }
    @Test
    public void createUserWithValidRole(){
        String name = faker.name().firstName();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        CreateUserRequest createUserRequest = new CreateUserRequest(name, email, password, password,"Maxim");
        Response response = postRequest("/api/auth/register", 400, createUserRequest);

        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains("Role must be either 'admin' or 'user'"));
    }
    @Test
    public void createUserWithIncorrectConfirmPassword(){
        String name = faker.name().firstName();
        String email = faker.internet().emailAddress();

        CreateUserRequest createUserRequest = new CreateUserRequest(name, email, "Test123456", "Test123458", "user");
        Response response = postRequest("/api/auth/register", 400, createUserRequest);

        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains("Password mismatch!"));
        assertTrue(responseBody.contains("BAD_REQUEST"));
    }
    @Test
    public void createUserWithEmptyAllFields(){
        CreateUserRequest createUserRequest = new CreateUserRequest();
        Response response = postRequest("/api/auth/register", 400, createUserRequest);

        String responseBody = response.getBody().asString();
        String expectedResponseBody = "{"
                + "password:[Password cannot be empty],"
                + "role:[must not be null],"
                + "confirmPassword:[You need to confirm your password],"
                + "email:[Email cannot be empty]"
                + "}";
    }
    //commit

}

