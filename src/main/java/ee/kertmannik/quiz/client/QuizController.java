package ee.kertmannik.quiz.client;

import ee.kertmannik.quiz.client.model.Question;

import java.io.IOException;

public class QuizController {

    private Question question;
    private static final String ANSWER_URL = "https://fortumo-intern-quiz.herokuapp.com/answer";
    private static final String GIST_URL = "https://fortumo-intern-quiz.herokuapp.com/question";
    private QuestionSupplier questionSupplier;
    private AnswerSupplier answerSupplier;

    public QuizController(String username) {
        this.questionSupplier = new QuestionSupplier(username, new QuestionRequest(GIST_URL));
        this.answerSupplier =
                new AnswerSupplier(username, new AnswerRequest(ANSWER_URL), new CommandLineScanner());
    }

    QuizController(QuestionSupplier questionSupplier) {
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

    public void postAnswer() {
        String serverAnswer = this.answerSupplier.getAndSendUserAnswer(this.question.getQuestionId());
        System.out.println(serverAnswer);
    }
}
