package net.codestudent.main;

//class that does nothing but storing methods to print out every part of the story
public class Story {

    public static void printIntro(){
        GameLogic.clearConsole();
        GameLogic.printSeparator(30);
        System.out.println("STORY");
        GameLogic.printSeparator(30);
        System.out.println("Live long");
        GameLogic.anythingToContinue();
    }

    public static void printFirstActIntro(){
        GameLogic.clearConsole();
        GameLogic.printSeparator(30);
        System.out.println("STORY1INTRO");
        GameLogic.printSeparator(30);
        System.out.println("Live long");
        GameLogic.anythingToContinue();
    }

    public static void printFirstActOutro(){
        GameLogic.clearConsole();
        GameLogic.printSeparator(30);
        System.out.println("STORY1OUTRO");
        GameLogic.printSeparator(30);
        System.out.println("Live long");
        GameLogic.anythingToContinue();
    }

    public static void printSecondActIntro(){
        GameLogic.clearConsole();
        GameLogic.printSeparator(30);
        System.out.println("STORY2INTRO");
        GameLogic.printSeparator(30);
        System.out.println("Live long");
        GameLogic.anythingToContinue();
    }

    public static void printSecondActOutro(){
        GameLogic.clearConsole();
        GameLogic.printSeparator(30);
        System.out.println("STORY2OUTRO");
        GameLogic.printSeparator(30);
        System.out.println("Live long");
        GameLogic.anythingToContinue();
    }

    public static void printThirdActIntro(){
        GameLogic.clearConsole();
        GameLogic.printSeparator(30);
        System.out.println("STORY3INTRO");
        GameLogic.printSeparator(30);
        System.out.println("Live long");
        GameLogic.anythingToContinue();
    }

    public static void printThirdActOutro(){
        GameLogic.clearConsole();
        GameLogic.printSeparator(30);
        System.out.println("STORY3OUTRO");
        GameLogic.printSeparator(30);
        System.out.println("Live long");
        GameLogic.anythingToContinue();
    }

    public static void printFourthActIntro(){
        GameLogic.clearConsole();
        GameLogic.printSeparator(30);
        System.out.println("STORY4INTRO");
        GameLogic.printSeparator(30);
        System.out.println("Live long");
        GameLogic.anythingToContinue();
    }

    public static void printFourthActOutro(){
        GameLogic.clearConsole();
        GameLogic.printSeparator(30);
        System.out.println("STORYOUTRO");
        GameLogic.printSeparator(30);
        System.out.println("Live long");
        GameLogic.anythingToContinue();
    }

    public static void printEnd(Player player){
        GameLogic.clearConsole();
        GameLogic.printSeparator(30);
        System.out.println("Congratulations, " + player.name);
        GameLogic.printSeparator(30);
        System.out.println("Live long");
    }
}
