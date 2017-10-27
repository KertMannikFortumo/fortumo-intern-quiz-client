package ee.kertmannik.quiz.client;

import java.util.Scanner;

public class CommandLineScanner {

    public String getUserInputWithMessagePrinted(String question) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(question);
        return scanner.nextLine();
    }
}
