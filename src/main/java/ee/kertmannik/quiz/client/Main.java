package ee.kertmannik.quiz.client;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        PlayerGreeting PlayerGreeting = new PlayerGreeting();

        String username = PlayerGreeting.getPlayerName();
        PlayerGreeting.greetPlayer(username);

        try {
            QuestionController questionController = new QuestionController(username);
            questionController.getQuestion();
        } catch (QuizException exception) {
            System.out.println("\n" + exception.getMessage());
        }
    }
}
