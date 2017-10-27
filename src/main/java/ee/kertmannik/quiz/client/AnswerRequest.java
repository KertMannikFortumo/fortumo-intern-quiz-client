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
    private String playerName;

    AnswerRequest(String url, String username) {
        this.url = url;
        this.playerName = username;
    }

    public String postAnswerToServer(String answer) throws IOException {
        final OkHttpClient client = new OkHttpClient();
        final RequestBody body = RequestBody.create(MediaType.parse("text/plain"), answer);
        final Request request = new Request.Builder()
                .url(url)
                .addHeader("x-player-name", this.playerName)
                .post(body)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (SocketTimeoutException exception) {
            throw new QuizException("POST request timed out.");
        } catch (UnknownHostException exception) {
            throw new QuizException("Incorrect URL link");
        } finally {
            if (response != null && response.body() != null) {
                response.body().close();
            }
        }
    }
}
