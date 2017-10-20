package ee.kertmannik.quiz.client;

import java.util.Scanner;

public class GreetingPlayer {

    public String getPlayerName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        return scanner.next();
    }

    public String greetPlayer(String playerName) {
        return "Hello, " + playerName;
    }
}
