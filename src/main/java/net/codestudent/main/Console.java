package net.codestudent.main;

public interface Console {
    void heading(String title);
    void separator(int length);
    void print(String message);
    void clear();
    int askInt(String prompt, int maxChoices);
    String askLine();
    void waitForContinue();
}
