package ee.kertmannik.quiz.client;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class QuestionRequest {

    private String url;
    private final OkHttpClient client = new OkHttpClient();

    QuestionRequest(String url) {
        this.url = url;
    }

    public String getQuestionFromServer(String playerName) throws IOException {
        final Request request = new Request.Builder()
                .url(url)
                .addHeader("x-player-name", playerName)
                .build();
        Response response = null;
        try {
            response = this.client.newCall(request).execute();
            return response.body().string();
        } catch (SocketTimeoutException exception) {
            throw new QuizException("GET request timed out.");
        } catch (UnknownHostException exception) {
            throw new QuizException("Incorrect URL link");
        } finally {
            if (response != null && response.body() != null) {
                response.body().close();
            }
        }
    }
}
