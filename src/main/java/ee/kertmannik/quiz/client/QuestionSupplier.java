package ee.kertmannik.quiz.client;

import com.google.gson.Gson;
import ee.kertmannik.quiz.client.model.Question;

import java.io.IOException;

public class QuestionSupplier {

    private QuestionRequest questionRequest;
    private String username;

    public QuestionSupplier(String username, QuestionRequest questionRequest) {
        this.username = username;
        this.questionRequest = questionRequest;
    }

    public Question requestQuestion() throws IOException {
        final String result = this.questionRequest.getQuestionFromServer(this.username);
        return fromJsonToJava(result);
    }

    private Question fromJsonToJava(String rawQuestion) throws QuizException {
        final Gson gson = new Gson();
        return gson.fromJson(rawQuestion, Question.class);
    }
}
