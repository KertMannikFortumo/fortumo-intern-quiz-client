package ee.kertmannik.quiz.client;

import com.google.gson.Gson;
import ee.kertmannik.quiz.client.model.Question;

import java.io.IOException;

public class QuestionController {

    private Question question;

    private Question getQuestion(String username) throws IOException, QuizException {
        final QuestionRequest questionRequest = new QuestionRequest();
        final String result = questionRequest.getQuestionFromServer(username);
        return this.fromJsonToJava(result);
    }

    public String displayableQuestion(String username) throws IOException, QuizException {
        if (this.question == null) {
            this.question = this.getQuestion(username);
        }
        return "("
                + this.question.getCategory()
                + ", "
                + this.question.getDifficulty()
                + ") "
                + this.question.getQuestion();
    }

    public Question fromJsonToJava(String rawQuestion) {
        final Gson gson = new Gson();
        return gson.fromJson(rawQuestion, Question.class);
    }
}
