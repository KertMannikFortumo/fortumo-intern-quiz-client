package ee.kertmannik.quiz.client.model;

import com.google.gson.annotations.SerializedName;

public class Answer {

    @SerializedName("question-id")
    private String questionId;
    private String answer;

    public Answer(String questionId, String answer){
        this.questionId = questionId;
        this.answer = answer;
    }

    public String getAnswer() {
        return this.answer;
    }

    public String getQuestionId() {
        return this.questionId;
    }
}
