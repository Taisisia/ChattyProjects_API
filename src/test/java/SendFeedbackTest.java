import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SendFeedbackTest extends BaseTest {

    @Test
    public void sendFeedback (){

        SendFeedbackRequest sendFeedbackRequest = new SendFeedbackRequest("John", "testQA303@gmail.com", "Test123456");
        Response response1 = postRequest("/api/feedback", 201, sendFeedbackRequest);

        SendFeedbackRequest feedbackRequest = response1.as(SendFeedbackRequest.class);
        SendFeedbackResponse feedbackResponse = response1.as(SendFeedbackResponse.class);


        // 1. Check email in request equals email in response
        assertEquals(feedbackRequest.getEmail(), feedbackResponse.getEmail());
        // 2. Check that content is not empty
        assertFalse(feedbackResponse.getContent().isEmpty());




    }
}
