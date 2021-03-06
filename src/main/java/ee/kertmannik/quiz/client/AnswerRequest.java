package ee.kertmannik.quiz.client;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class AnswerRequest {

    private String url;
    private OkHttpClient client = new OkHttpClient();

    AnswerRequest(String url) {
        this.url = url;
    }

    public String postAnswerToServer(String answer, String playerName) {
        final RequestBody body = RequestBody.create(MediaType.parse("text/plain"), answer);
        Request request;
        try {
            request = new Request.Builder()
                .url(this.url)
                .addHeader("x-player-name", playerName)
                .post(body)
                .build();
        } catch (IllegalArgumentException exception) {
            throw new QuizException("Illegal url", exception);
        }

        Response response = null;
        try {
            response = this.client.newCall(request).execute();
            return response.body().string();
        } catch (SocketTimeoutException exception) {
            throw new QuizException("POST request timed out.", exception);
        } catch (UnknownHostException exception) {
            throw new QuizException("Incorrect URL link", exception);
        } catch (IOException exception) {
            throw new QuizException("An input or output operation failed/interpreted.", exception);
        } finally {
            if (response != null && response.body() != null) {
                response.body().close();
            }
        }
    }
}
