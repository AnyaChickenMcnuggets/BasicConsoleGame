package net.codestudent.main;

public class Story {

    public static void printIntro(Console console) {
        console.clear();
        console.separator(30);
        console.print("STORY");
        console.separator(30);
        console.print("Live long");
        console.waitForContinue();
    }

    public static void printFirstActIntro(Console console) {
        console.clear();
        console.separator(30);
        console.print("STORY1INTRO");
        console.separator(30);
        console.print("Live long");
        console.waitForContinue();
    }

    public static void printFirstActOutro(Console console) {
        console.clear();
        console.separator(30);
        console.print("STORY1OUTRO");
        console.separator(30);
        console.print("Live long");
        console.waitForContinue();
    }

    public static void printSecondActIntro(Console console) {
        console.clear();
        console.separator(30);
        console.print("STORY2INTRO");
        console.separator(30);
        console.print("Live long");
        console.waitForContinue();
    }

    public static void printSecondActOutro(Console console) {
        console.clear();
        console.separator(30);
        console.print("STORY2OUTRO");
        console.separator(30);
        console.print("Live long");
        console.waitForContinue();
    }

    public static void printThirdActIntro(Console console) {
        console.clear();
        console.separator(30);
        console.print("STORY3INTRO");
        console.separator(30);
        console.print("Live long");
        console.waitForContinue();
    }

    public static void printThirdActOutro(Console console) {
        console.clear();
        console.separator(30);
        console.print("STORY3OUTRO");
        console.separator(30);
        console.print("Live long");
        console.waitForContinue();
    }

    public static void printFourthActIntro(Console console) {
        console.clear();
        console.separator(30);
        console.print("STORY4INTRO");
        console.separator(30);
        console.print("Live long");
        console.waitForContinue();
    }

    public static void printFourthActOutro(Console console) {
        console.clear();
        console.separator(30);
        console.print("STORYOUTRO");
        console.separator(30);
        console.print("Live long");
        console.waitForContinue();
    }

    public static void printEnd(Console console, Player player) {
        console.clear();
        console.separator(30);
        console.print("Congratulations, " + player.name);
        console.separator(30);
        console.print("Live long");
    }
}
