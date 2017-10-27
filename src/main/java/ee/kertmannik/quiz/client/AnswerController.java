package ee.kertmannik.quiz.client;

import com.google.gson.Gson;
import ee.kertmannik.quiz.client.model.Answer;

public class AnswerController {

    private String questionId;
    private String username;
    private String serverAnswerURL;

    public AnswerController(String questionId, String username, String url) {
        this.questionId = questionId;
        this.username = username;
        this.serverAnswerURL = url;
    }

    public void getAndSendUserAnswer() {
        String userAnswer = getAnswer();
        String jsonAnswer = convertAnswer(userAnswer);
        try {
            sendPostRequest(jsonAnswer);
        } catch (Exception exception) {
            throw new QuizException("Problem sending answer to server.");
        }
    }

    private String getAnswer() {
        CommandLineScanner commandLineScanner = new CommandLineScanner();
        return commandLineScanner.getUserInputWithMessagePrinted("");
    }

    private String convertAnswer(String userAnswer) {
        Answer answer = new Answer(this.questionId, userAnswer);
        return fromJavaToJson(answer);
    }

    private void sendPostRequest(String jsonAnswer) throws Exception {
        AnswerRequest answerRequest = new AnswerRequest(this.serverAnswerURL, this.username);
        String serverAnswer = answerRequest.postAnswerToServer(jsonAnswer);
        System.out.println(serverAnswer);
    }

    private String fromJavaToJson(Answer rawAnswer) throws QuizException {
        final Gson gson = new Gson();
        return gson.toJson(rawAnswer);
    }
}
