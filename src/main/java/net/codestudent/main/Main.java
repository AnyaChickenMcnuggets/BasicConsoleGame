package net.codestudent.main;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args){
        //принудительный UTF-8 вывод для кириллицы (не зависит от системной кодовой страницы)
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        //начало игры
        GameLogic.startGame();
    }

}
