package net.codestudent.main;

public class Main {
    public static void main(String[] args) {
        Console console = new TerminalConsole();
        new Game(console).start();
    }
}
