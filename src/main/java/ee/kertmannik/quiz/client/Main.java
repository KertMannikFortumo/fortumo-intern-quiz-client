package ee.kertmannik.quiz.client;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {// create a scanner so we can read the command-line input
        String result = getMessageFromServer("https://fortumo-intern-quiz.herokuapp.com/question", "Kert");
        System.out.println(result);
    }

    public static String getMessageFromServer(String url, String candidateName) throws IOException {
        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .addHeader("x-player-name", candidateName)
                .build();
        final Response response = client.newCall(request).execute();
        final int code = response.code();
        if (code >= 200 && code < 300) {
            return response.body().string();
        } else {
            return "Response code is not OK!";
        }
    }
}
