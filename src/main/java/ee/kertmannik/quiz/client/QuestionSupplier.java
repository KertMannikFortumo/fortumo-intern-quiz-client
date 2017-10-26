package ee.kertmannik.quiz.client;

import com.google.gson.Gson;
import ee.kertmannik.quiz.client.model.Question;

import java.io.IOException;

public class QuestionSupplier {

    private QuestionRequest questionRequest;
    private String GIST_URL = "https://fortumo-intern-quiz.herokuapp.com/question";
    private String username;

    public QuestionSupplier (String username) {
        this.username = username;
        this.questionRequest = new QuestionRequest(GIST_URL);
    }

    public Question requestQuestion() throws IOException, QuizException {
        final String result = this.questionRequest.getQuestionFromServer(this.username);
        return fromJsonToJava(result);
    }

    private Question fromJsonToJava(String rawQuestion) {
        final Gson gson = new Gson();
        return gson.fromJson(rawQuestion, Question.class);
    }

}
