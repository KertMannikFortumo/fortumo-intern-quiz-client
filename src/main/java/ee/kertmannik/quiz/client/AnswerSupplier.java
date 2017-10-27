package ee.kertmannik.quiz.client;

import com.google.gson.Gson;
import ee.kertmannik.quiz.client.model.Answer;

public class AnswerSupplier {

    private String username;
    private String serverAnswerURL;

    public AnswerSupplier(String username, String url) {
        this.username = username;
        this.serverAnswerURL = url;
    }

    public String getAndSendUserAnswer(String questionId) {
        String userAnswer = this.getAnswer();
        String jsonAnswer = this.convertAnswer(userAnswer, questionId);
        try {
            return this.sendPostRequest(jsonAnswer);
        } catch (Exception exception) {
            throw new QuizException("Problem sending answer to server.");
        }
    }

    private String getAnswer() {
        CommandLineScanner commandLineScanner = new CommandLineScanner();
        return commandLineScanner.getUserInputWithoutMessage();
    }

    private String convertAnswer(String userAnswer, String questionId) {
        Answer answer = new Answer(questionId, userAnswer);
        return fromJavaToJson(answer);
    }

    private String fromJavaToJson(Answer rawAnswer) throws QuizException {
        final Gson gson = new Gson();
        return gson.toJson(rawAnswer);
    }

    private String sendPostRequest(String jsonAnswer) throws Exception {
        AnswerRequest answerRequest = new AnswerRequest(this.serverAnswerURL);
        return answerRequest.postAnswerToServer(jsonAnswer, this.username);
    }
}
