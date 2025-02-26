import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpdateUserTest extends BaseTest {
    Faker faker = new Faker();


    @Test
    public void updateUser() {
        String name = faker.name().firstName();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        String nameUpdate = faker.name().firstName();

        CreateUserRequest createUserRequest = new CreateUserRequest(name,email ,password , password, "user");
        Response responseCreateUser = postRequest("/api/auth/register", 201, createUserRequest);


        LoginUserRequest loginUserRequest = new LoginUserRequest(email, password);
        Response response = postRequest("/api/auth/login", 200, loginUserRequest);
        LoginUserResponse responseBody = response.as(LoginUserResponse.class);

        String token = responseBody.getAccessToken();

        Response updateUser = getRequest("/api/me", 200, token);
        UserDataResponse userInfo = updateUser.as(UserDataResponse.class);
        String userId = userInfo.getId();

        UserDataResponse updateRequest = new UserDataResponse();
        updateRequest.setName(nameUpdate);
        Response responseUpdate = putRequest("/api/users/" + userId, 200, updateRequest, token);

        // 1. Check that name changed
        Response updatedUserResponse = getRequest("/api/me", 200, token);
        UserDataResponse updatedUserInfo = updatedUserResponse.as(UserDataResponse.class);

        assertEquals(nameUpdate, updatedUserInfo.getName());
    }
}