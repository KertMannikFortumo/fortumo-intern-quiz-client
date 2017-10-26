package ee.kertmannik.quiz.client;

import com.google.gson.Gson;
import ee.kertmannik.quiz.client.model.Question;

import java.io.IOException;

public class QuestionController {

    private String username;
    private String GIST_URL = "https://fortumo-intern-quiz.herokuapp.com/question";
    private Question question;
    private QuestionRequest questionRequest;

    QuestionController(String username) {
        this.username = username;
        this.questionRequest = new QuestionRequest(GIST_URL);
    }

    public void getQuestion() throws IOException, QuizException {
        final String rawQuestion = this.requestQuestion();
        this.question = this.fromJsonToJava(rawQuestion);
        System.out.println("\n("
                + this.question.getCategory()
                + ", "
                + this.question.getDifficulty()
                + ") "
                + this.question.getQuestion());
    }

    private String requestQuestion() throws IOException, QuizException {
        final String result = this.questionRequest.getQuestionFromServer(this.username);
        return result;
    }

    private Question fromJsonToJava(String rawQuestion) {
        final Gson gson = new Gson();
        return gson.fromJson(rawQuestion, Question.class);
    }
}
