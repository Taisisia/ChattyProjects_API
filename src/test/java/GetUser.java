import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class GetUser extends BaseTest {
    @Test
    public void getUserData() {
        LoginUserRequest requestBody = new LoginUserRequest("testQA303@gmail.com", "Test123456");
        Response response = postRequest("/api/auth/login", 200, requestBody);

        LoginUserResponse responseBody = response.as(LoginUserResponse.class);
        String token = responseBody.getAccessToken();

        Response responseSecond = getRequest("/api/me", 200, token);
        UserDataResponse userInfo = responseSecond.as(UserDataResponse.class);
        System.out.println("response" + response);
        System.out.println("responseSecond" + responseSecond);
//{
//    "id": "7586bfba-3383-4348-830f-c4629acac7fa",
//    "name": null,
//    "surname": null,
//    "phone": null,
//    "email": "testQA303@gmail.com",
//    "role": "USER",
//    "gender": null,
//    "birthDate": null,
//    "avatarUrl": null,
//    "backgroundUrl": null
//}
    }
    @Test
    public void getAdminData() {
        LoginUserRequest requestBody = new LoginUserRequest("AdminTestQA303@gmail.com", "Test123456");
        Response responsePostRequest = postRequest("/api/auth/login", 200, requestBody);

        LoginUserResponse responseBody = responsePostRequest.as(LoginUserResponse.class);
        String token = responseBody.getAccessToken();

        Response responseGetRequest = getRequest("/api/me", 200, token);
        UserDataResponse userInfo = responseGetRequest.as(UserDataResponse.class);
        System.out.println("response" + responsePostRequest);
        System.out.println("responseGetRequest" + responseGetRequest);
//        "id": "1cc46035-ca6c-46fd-9f17-d99dbe7978a0",
//                "name": null,
//                "surname": null,
//                "phone": null,
//                "email": "AdminTestQA303@gmail.com",
//                "role": "ADMIN",
//                "gender": null,
//                "birthDate": null,
//                "avatarUrl": null,
//                "backgroundUrl": null

    }
}
