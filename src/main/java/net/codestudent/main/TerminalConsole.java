package net.codestudent.main;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class TerminalConsole implements Console {

    private final Scanner scanner = new Scanner(System.in);
    private final PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

    @Override
    public void clear() {
        for (int i = 0; i < 100; i++)
            out.println();
    }

    @Override
    public void separator(int length) {
        for (int i = 0; i < length; i++)
            out.print("-");
        out.println();
    }

    @Override
    public void heading(String title) {
        separator(30);
        out.println(title);
        separator(30);
    }

    @Override
    public void print(String message) {
        out.println(message);
    }

    @Override
    public int askInt(String prompt, int maxChoices) {
        int input;
        do {
            out.println(prompt);
            try {
                input = Integer.parseInt(scanner.next());
            } catch (Exception e) {
                input = -1;
                out.println("Please enter a number!");
            }
        } while (input < 1 || input > maxChoices);
        return input;
    }

    @Override
    public String askLine() {
        return scanner.next();
    }

    @Override
    public void waitForContinue() {
        out.println("\nPress enter to continue...");
        scanner.next();
    }
}
