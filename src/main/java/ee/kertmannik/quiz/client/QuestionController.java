package ee.kertmannik.quiz.client;

import ee.kertmannik.quiz.client.model.Question;

import java.io.IOException;

public class QuestionController {

    private Question question;
    private QuestionSupplier questionSupplier;

    QuestionController(String username) {
        this.questionSupplier = new QuestionSupplier(username);
    }

    QuestionController(QuestionSupplier questionSupplier) {
        this.questionSupplier = questionSupplier;
    }

    public void getQuestion() throws IOException, QuizException {
        this.question = this.questionSupplier.requestQuestion();
        System.out.print("\n("
                + this.question.getCategory()
                + ", "
                + this.question.getDifficulty()
                + ") "
                + this.question.getQuestion());
    }
}
