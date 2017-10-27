package ee.kertmannik.quiz.client;

import ee.kertmannik.quiz.client.model.Question;

import java.io.IOException;

public class QuizController {

    private Question question;
    private String ANSWER_URL = "https://fortumo-intern-quiz.herokuapp.com/answer";
    private QuestionSupplier questionSupplier;
    private AnswerSupplier answerSupplier;

    QuizController(String username) {
        this.questionSupplier = new QuestionSupplier(username);
        this.answerSupplier = new AnswerSupplier(username, this.ANSWER_URL);
    }

    QuizController(QuestionSupplier questionSupplier) {
        this.questionSupplier = questionSupplier;
    }

    QuizController(AnswerSupplier answerSupplier) {
        this.answerSupplier = answerSupplier;
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
