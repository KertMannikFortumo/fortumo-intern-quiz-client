package ee.kertmannik.quiz.client;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        PlayerGreeting PlaterGreeting = new PlayerGreeting();
        QuestionController questionController = new QuestionController();

        String username = PlaterGreeting.getPlayerName();
        PlaterGreeting.greetPlayer(username);

        try {
            String question = questionController.displayableQuestion(username);
            System.out.println(question);
        } catch (QuizException exception) {
            System.out.println(exception);
        }
    }
}
