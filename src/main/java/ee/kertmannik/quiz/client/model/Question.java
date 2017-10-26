package ee.kertmannik.quiz.client.model;

import java.util.List;

public class Question {

    private String questionId;
    private String question;
    private String category;
    private int difficulty;
    private transient List<String> correctAnswers;

    public Question(String questionId, String question, String category, int difficulty, List<String> correctAnswers) {
        this.questionId = questionId;
        this.question = question;
        this.category = category;
        this.difficulty = difficulty;
        this.correctAnswers = correctAnswers;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setCorrectAnswers(List<String> correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public List<String> getCorrectAnswers() {
        return correctAnswers;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public String getCategory() {
        return category;
    }

    public String getQuestion() {
        return question;
    }

    public String getQuestionId() {
        return questionId;
    }
}
