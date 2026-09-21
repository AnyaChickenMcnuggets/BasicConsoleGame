package net.codestudent.main;

public class Player extends Character {

    public int numAtkUpgrades, numDefUpgrades;

    int gold, restsLeft, pots, lvl, maxXP;

    public String[] atkUpgrades = {"Атака 1", "Атака 2", "Атака 3", "Атака 4"};
    public String[] defUpgrades = {"Защита 1", "Защита 2", "Защита 3", "Защита 4"};

    private final Console console;

    public Player(String name, Console console) {
        super(name, 100, 0);
        this.console = console;
        this.numAtkUpgrades = 0;
        this.numDefUpgrades = 0;
        this.lvl = 1;
        this.gold = 5;
        this.restsLeft = 1;
        this.pots = 0;
        this.maxXP = 15;
        chooseTrait();
    }

    @Override
    public int attack() {
        return (int) (Math.random() * (numAtkUpgrades * 3 + numDefUpgrades) + 8);
    }

    @Override
    public int defend() {
        return (int) (Math.random() * (numDefUpgrades * 3 + numAtkUpgrades) + 4);
    }

    public void lvlUP() {
        console.clear();
        console.heading("Ваш уровень увеличился. Здоровье полность востановлено");
        console.waitForContinue();
        maxXP += 10;
        lvl += 1;
        hp = maxHp;
        chooseTrait();
    }

    public void chooseTrait() {
        console.clear();
        console.heading("Выберите улучшение: ");
        console.print("(1) " + atkUpgrades[numAtkUpgrades]);
        console.print("(2) " + defUpgrades[numDefUpgrades]);
        int input = console.askInt("-> ", 2);
        console.clear();
        if (input == 1) {
            console.heading("Вы выбрали " + atkUpgrades[numAtkUpgrades] + "!");
            numAtkUpgrades++;
        } else {
            console.heading("Вы выбрали " + defUpgrades[numDefUpgrades] + "!");
            maxHp += 5;
            hp += 5;
            numDefUpgrades++;
        }
        console.waitForContinue();
    }
}
