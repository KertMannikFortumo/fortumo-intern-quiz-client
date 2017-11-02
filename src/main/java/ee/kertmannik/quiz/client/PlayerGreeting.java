package ee.kertmannik.quiz.client;

public class PlayerGreeting {

    public String getPlayerName() {
        CommandLineScanner commandLineScanner = new CommandLineScanner();
        return commandLineScanner.getUserInputWithMessage("Enter your name: ");
    }

    public void greetPlayer(String playerName) {
        System.out.println("Hello, " + playerName + ".");
    }
}
