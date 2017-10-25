package ee.kertmannik.quiz.client;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        GreetingPlayer greetingPlayer = new GreetingPlayer();
        String username = greetingPlayer.getPlayerName();
        System.out.println(greetingPlayer.greetPlayer(username));
    }
}
