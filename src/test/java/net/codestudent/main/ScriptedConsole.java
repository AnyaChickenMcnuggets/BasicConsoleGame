package net.codestudent.main;

import java.util.ArrayDeque;
import java.util.Deque;

class ScriptedConsole implements Console {

    private final Deque<Integer> intAnswers = new ArrayDeque<>();
    private final Deque<String> lineAnswers = new ArrayDeque<>();

    ScriptedConsole withInt(int value) {
        intAnswers.add(value);
        return this;
    }

    ScriptedConsole withLine(String value) {
        lineAnswers.add(value);
        return this;
    }

    @Override
    public void heading(String title) {}

    @Override
    public void separator(int length) {}

    @Override
    public void print(String message) {}

    @Override
    public void clear() {}

    @Override
    public int askInt(String prompt, int maxChoices) {
        if (intAnswers.isEmpty())
            throw new IllegalStateException("No scripted int answer left for prompt: " + prompt);
        return intAnswers.poll();
    }

    @Override
    public String askLine() {
        if (lineAnswers.isEmpty())
            throw new IllegalStateException("No scripted line answer left");
        return lineAnswers.poll();
    }

    @Override
    public void waitForContinue() {}
}
