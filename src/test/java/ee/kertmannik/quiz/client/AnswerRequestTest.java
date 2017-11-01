package ee.kertmannik.quiz.client;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

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

    @Test
    public void should_throw_QuizException_if_url_is_incorrect() throws IOException, QuizException {
        //given
        String anyURL = "anyURL";
        String answer = "anyAnswer";
        String player = "anyPlayer";
        AnswerRequest answerRequest = new AnswerRequest(anyURL);

        //when
        try {
            String result = answerRequest.postAnswerToServer(answer, player);
            fail("Should throw QuizException if invalid url link is given");
        } catch (QuizException expected) {

        }
    }
}