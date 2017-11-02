package ee.kertmannik.quiz.client;

import java.util.List;
import java.util.Scanner;

public class CommandLineScanner {

    public String getUserInputWithMessage(String question) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(question);
        return scanner.nextLine();
    }

    public String getUserInputWithoutMessage() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public String getPlayerDecisionWithValidation(List<String> answers, String message) {
        List<String> validAnswers = answers;
        String playerDecision = "";
        while (!validAnswers.contains(playerDecision)) {
            playerDecision = this.getUserInputWithMessage(message);
        }
        return playerDecision;
    }
}
