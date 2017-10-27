package ee.kertmannik.quiz.client;

import java.util.Scanner;

public class CommandLineScanner {

    public String getUserInputWithMessage(String question) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(question);
        return scanner.nextLine();
    }

    public String getUserInputWithoutMessage() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
