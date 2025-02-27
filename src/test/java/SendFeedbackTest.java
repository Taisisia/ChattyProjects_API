import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SendFeedbackTest extends BaseTest {
    Faker faker = new Faker();

    @Test
    public void sendFeedback (){
        String content = faker.lorem().sentence();
        SendFeedbackRequest sendFeedbackRequest = new SendFeedbackRequest("John", "testQA303@gmail.com", content);
        Response response = postRequest("/api/feedback", 201, sendFeedbackRequest);

        SendFeedbackRequest feedbackRequest = response.as(SendFeedbackRequest.class);
        SendFeedbackResponse feedbackResponse = response.as(SendFeedbackResponse.class);


        assertEquals(feedbackRequest.getEmail(), feedbackResponse.getEmail());
        assertFalse(feedbackResponse.getContent().isEmpty());
    }

    @Test
    public void sendFeedbackEmailEmpty (){
        String content = faker.lorem().sentence();
        SendFeedbackRequest sendFeedbackRequest = new SendFeedbackRequest("John", "", content);
        Response response = postRequest("/api/feedback", 400, sendFeedbackRequest);

        Assertions.assertEquals(400, response.getStatusCode());

        String responseBody = response.getBody().asString();
        Assertions.assertTrue(responseBody.contains("Email can not be empty!"));
    }
    @Test
    public void sendFeedbackContentEmpty (){
        String content = "";
        SendFeedbackRequest sendFeedbackRequest = new SendFeedbackRequest("John", "testQA303@gmail.com", content);
        Response response = postRequest("/api/feedback", 400, sendFeedbackRequest);

        Assertions.assertEquals(400, response.getStatusCode());

        String responseBody = response.getBody().asString();
        Assertions.assertTrue(responseBody.contains("Content can not be empty!"));
        Assertions.assertTrue(responseBody.contains("Content must contain from 1 to 30 characters"));

    }
    @Test
    public void sendFeedbackNameEmpty (){
        String content = faker.lorem().sentence();
        SendFeedbackRequest sendFeedbackRequest = new SendFeedbackRequest("", "testQA303@gmail.com", content);
        Response response = postRequest("/api/feedback", 400, sendFeedbackRequest);

        Assertions.assertEquals(400, response.getStatusCode());

        String responseBody = response.getBody().asString();
        Assertions.assertTrue(responseBody.contains("Name must contain from 1 to 30 characters"));
        Assertions.assertTrue(responseBody.contains("Name can not be empty!"));

    }
}
