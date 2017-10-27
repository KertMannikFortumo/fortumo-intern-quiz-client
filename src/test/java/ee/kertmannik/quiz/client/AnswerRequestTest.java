package ee.kertmannik.quiz.client;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AnswerRequestTest {

    @Test
    public void should_return_response_body_as_String() throws Exception {
        //given
        MockWebServer server = new MockWebServer();
        server.start();
        HttpUrl baseUrl = server.url("/v1/chat/");
        AnswerRequest answerRequest = new AnswerRequest(baseUrl.toString());
        server.enqueue(new MockResponse().setBody("Lars is the best!!!"));
        String answer = "{\"questionId\":\"anyId\",\"answer\":\"anyAnswer\"}";

        //when
        String result = answerRequest.postAnswerToServer(answer, "anyPlayer");

        //then
        assertThat(result).isEqualTo("Lars is the best!!!");
    }
}