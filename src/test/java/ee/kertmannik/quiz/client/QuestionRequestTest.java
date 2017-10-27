package ee.kertmannik.quiz.client;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class QuestionRequestTest {

    @Test
    public void Should_return_response_Body_as_String() throws Exception {
        //given
        MockWebServer server = new MockWebServer();
        server.start();
        HttpUrl baseUrl = server.url("/v1/chat/");
        QuestionRequest questionRequest = new QuestionRequest(baseUrl.toString());
        server.enqueue(new MockResponse().setBody("Lars is the best!!!"));
        //when
        String result = questionRequest.getQuestionFromServer("anyPlayer");
        //then
        assertThat(result).isEqualTo("Lars is the best!!!");
    }

    @Test
    public void should_throw_QuizException_if_url_is_incorrect() throws IOException, QuizException {
        //given
        String anyURL = "anyURL";
        QuestionRequest questionRequest = new QuestionRequest(anyURL);

        //when
        try {
            String result = questionRequest.getQuestionFromServer("anyPlayer");
            fail("Should throw QuizException if invalid url link is given");
        } catch (QuizException expected) {

        }
    }
}