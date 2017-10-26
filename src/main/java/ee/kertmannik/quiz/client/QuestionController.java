package ee.kertmannik.quiz.client;

import com.google.gson.Gson;
import ee.kertmannik.quiz.client.model.Question;

import java.io.IOException;

public class QuestionController {

    private Question question;
    private String username;
    private String ANSWER_URL = "https://fortumo-intern-quiz.herokuapp.com/answer";
    private QuestionSupplier questionSupplier;

    QuestionController(String username) {
        this.username = username;
        this.questionSupplier = new QuestionSupplier(username);
    }

    QuestionController(QuestionSupplier questionSupplier) {
        this.questionSupplier = questionSupplier;
    }

    public void getQuestion() throws IOException {
        this.question = this.questionSupplier.requestQuestion();
        System.out.print("\n("
                + this.question.getCategory()
                + ", "
                + this.question.getDifficulty()
                + ") "
                + this.question.getQuestion());
    }

    public void postAnswer() throws Exception {
        CommandLineScanner commandLineScanner = new CommandLineScanner();
        AnswerRequest answerRequest = new AnswerRequest(ANSWER_URL, this.username);
        String userAnswer = commandLineScanner.getUserInputWithMessagePrinted("");
        String jsonAnswer = fromJsonToJava(userAnswer);
        System.out.println(jsonAnswer);
        String serverAnswer = answerRequest.postAnswerToServer(userAnswer, this.username);
        System.out.println(serverAnswer);
    }


    private String fromJsonToJava(String rawAnswer) throws QuizException {
        final Gson gson = new Gson();
        return gson.toJson(rawAnswer);
    }
}
