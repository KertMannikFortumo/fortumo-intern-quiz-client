package ee.kertmannik.quiz.client;

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

    public void postAnswer() {
        AnswerController answerController = new AnswerController(this.question.getQuestionId(), this.username,
                this.ANSWER_URL);
        answerController.getAndSendUserAnswer();
    }
}
