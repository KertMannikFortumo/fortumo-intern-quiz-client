package ee.kertmannik.quiz.client;

import ee.kertmannik.quiz.client.model.Question;

import java.io.IOException;
import java.util.Arrays;

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

    public QuizController(AnswerSupplier answerSupplier, QuestionSupplier questionSupplier,
            String answerStatus, Question question)
    {
        this.answerSupplier = answerSupplier;
        this.questionSupplier = questionSupplier;
        this.answerStatus = answerStatus;
        this.question = question;
    }

    public void startTheGame() throws IOException {
        this.getQuestion();
        this.postAnswer();
        this.decidingContinuation();
    }

    private void answerAgain() throws IOException {
        this.postAnswer();
        this.decidingContinuation();
    }

    public void getQuestion() throws IOException {
        this.question = this.questionSupplier.requestQuestion();
        System.out.println("(" + this.question.getCategory()
                + ", "
                + this.question.getDifficulty()
                + ") "
                + this.question.getQuestion());
    }

    public void postAnswer() {
        String serverAnswer = this.answerSupplier.getAndSendUserAnswer(this.question.getQuestionId());
        this.answerStatus = serverAnswer;
    }

    public void decidingContinuation() throws IOException {
        CommandLineScanner scanner = new CommandLineScanner();
        if ("wrong".equals(this.answerStatus)) {
            String playerDecision =
                    scanner.getPlayerDecisionWithValidation(Arrays.asList("y", "n"),
                            "Wrong answer! Do you want to continue? [y/n]");
            if (playerDecision.equals("y")) {
                System.out.println("Answer again: ");
                this.answerAgain();
            } else if (playerDecision.equals("n")) {
                System.out.println("Good game! THE END");
            }
        } else if ("correct".equals(this.answerStatus)) {
            String playerDecision = scanner.getPlayerDecisionWithValidation(Arrays.asList("y", "n"),
                    "Congrats! You answer was correct! Do you want to continue? [y/n]");
            if (playerDecision.equals("y")) {
                this.startTheGame();
            } else if (playerDecision.equals("n")) {
                System.out.println("Good game! THE END");
            }
        }
        this.answerStatus = "notAnswered";
    }
}
