package net.codestudent.main;

import javax.swing.*;

public class Player extends Character{

    //Integers чтобы хранить кол-во улучшений в каждой ветке
    public int numAtkUpgrades, numDefUpgrades;

    //дополнительные характеристики
    int gold, restsLeft, pots, lvl, maxXP;

    //Arrays чтобы хранить названия улучшений
    public String[] atkUpgrades = {"Атака 1", "Атака 2", "Атака 3", "Атака 4"};
    public String[] defUpgrades = {"Защита 1", "Защита 2", "Защита 3", "Защита 4"};

    //Player специальный конструктор
    public Player(String name) {
        //вызов конструктора от суперкласса
        super(name, 100, 0);
        //Установка улучшений на 0
        this.numAtkUpgrades = 0;
        this.numDefUpgrades = 0;
        //установка дополнительных характеристик
        this.lvl = 1;
        this.gold = 5;
        this.restsLeft = 1;
        this.pots = 0;
        this.maxXP = 15;
        //Игрок выбирает улучшение при создании персонажа
        chooseTrait();
    }

    //Player Методы
    @Override
    public int attack() {
        return (int)(Math.random()*(numAtkUpgrades*3 +numDefUpgrades) + 8);//макс базовый урон 10 или 9
    }

    @Override
    public int defend() {
        return (int)(Math.random()*(numDefUpgrades*3 +numAtkUpgrades) + 4);//макс базовая защита 7 или 5
    }

    //Герой повышает уровень
    public void lvlUP(){
        GameLogic.clearConsole();
        GameLogic.printHeading("Ваш уровень увеличился. Здоровье полность востановлено");
        GameLogic.anythingToContinue();
        maxXP += 10;
        lvl += 1;
        hp = maxHp;
        chooseTrait();
    }

    //Игрок выбирает улучшение
    public void chooseTrait(){
        GameLogic.clearConsole();
        GameLogic.printHeading("Выберите улучшение: ");
        System.out.println("(1) " + atkUpgrades[numAtkUpgrades]);
        System.out.println("(2) " + defUpgrades[numDefUpgrades]);
        //Получение выбора игрока
        int input = GameLogic.readInt("-> ", 2);
        GameLogic.clearConsole();
        //Разбираемся с обоими случаями
        if(input == 1){
            GameLogic.printHeading("Вы выбрали " + atkUpgrades[numAtkUpgrades] + "!");
            numAtkUpgrades++;
        }else {
            GameLogic.printHeading("Вы выбрали " + defUpgrades[numDefUpgrades] + "!");
            maxHp +=5;
            hp += 5;
            numDefUpgrades++;
        }
        GameLogic.anythingToContinue();
    }
}
