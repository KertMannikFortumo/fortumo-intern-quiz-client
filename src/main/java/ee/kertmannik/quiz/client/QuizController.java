package ee.kertmannik.quiz.client;

import ee.kertmannik.quiz.client.model.Question;

import java.io.IOException;

public class QuizController {

    private Question question;
    private static final String ANSWER_URL = "https://fortumo-intern-quiz.herokuapp.com/answer";
    private static final String GIST_URL = "https://fortumo-intern-quiz.herokuapp.com/question";
    private QuestionSupplier questionSupplier;
    private AnswerSupplier answerSupplier;
    private String answerStatus = "notAnswered";

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
        this.answerStatus = serverAnswer;
        System.out.println(serverAnswer);
    }

    public void decidingContinuation() {
        CommandLineScanner scanner = new CommandLineScanner();
        if ("wrong".equals(this.answerStatus)) {
            String playerDecision = "";
            while (!playerDecision.equals("y") || !playerDecision.equals("n")) {
                playerDecision = scanner.getUserInputWithMessage("Wrong answer! Do you want to continue? [y/n]");
            }
        } else if ("correct".equals(this.answerStatus)) {

        }
        this.answerStatus = "notAnswered";
    }
}
