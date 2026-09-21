package net.codestudent.main;

public class Game {

    private static final Act[] ACTS = {
        new Act(
            "Бесконечные Горы",
            new String[] {"Огр", "Огр", "Гоблин", "Гоблин", "Агрессивный Камень"},
            new Encounter[] {Encounter.BATTLE, Encounter.BATTLE, Encounter.BATTLE, Encounter.REST, Encounter.REST}
        ),
        new Act(
            "Призрачная Низина",
            new String[] {"Призрак", "Призрак", "Воришка", "Стая Волков", "Безглавый Рыцарь"},
            new Encounter[] {Encounter.BATTLE, Encounter.BATTLE, Encounter.BATTLE, Encounter.REST, Encounter.SHOP}
        ),
        new Act(
            "Кровавый Перевал",
            new String[] {"Бульник", "Стрыга", "Украинский Борщ", "Верховный Вампир", "Кровавая Жижа"},
            new Encounter[] {Encounter.BATTLE, Encounter.BATTLE, Encounter.BATTLE, Encounter.REST, Encounter.SHOP}
        ),
        new Act("Обитель Короля", new String[0], new Encounter[0])
    };

    private final Console console;

    Player player;
    boolean running;
    int actNumber = 1;
    Act currentAct = ACTS[0];

    public Game(Console console) {
        this.console = console;
    }

    public void start() {
        boolean nameSet = false;
        String name;
        console.clear();
        console.separator(40);
        console.separator(30);
        console.print("Последний Король");
        console.separator(30);
        console.separator(40);
        console.waitForContinue();

        do {
            console.clear();
            console.heading("Как вас зовут?");
            name = console.askLine();
            console.clear();
            console.heading("Вас зовут " + name + ".\nЭто правильно?");
            console.print("(1) Да!");
            console.print("(2) Нет, Я хочу изменить своё имя.");
            int input = console.askInt("-> ", 2);
            if (input == 1)
                nameSet = true;
        } while (!nameSet);

        Story.printIntro(console);

        player = new Player(name, console);

        Story.printFirstActIntro(console);

        running = true;

        loop();
    }

    void checkXP() {
        if (player.xp >= player.maxXP) {
            player.xp -= player.maxXP;
            player.lvlUP();
        }
    }

    void checkAct() {
        if (player.lvl >= 2 && actNumber == 1) {
            actNumber = 2;
            currentAct = ACTS[1];
            Story.printFirstActOutro(console);
            Story.printSecondActIntro(console);
        } else if (player.lvl >= 3 && actNumber == 2) {
            actNumber = 3;
            currentAct = ACTS[2];
            Story.printSecondActOutro(console);
            Story.printThirdActIntro(console);
        } else if (player.lvl >= 4 && actNumber == 3) {
            actNumber = 4;
            currentAct = ACTS[3];
            Story.printThirdActOutro(console);
            Story.printFourthActIntro(console);
            finalBattle();
        }
    }

    void randomEncounter() {
        int index = (int) (Math.random() * currentAct.encounterTable().length);
        switch (currentAct.encounterTable()[index]) {
            case BATTLE -> randomBattle();
            case REST -> takeRest();
            case SHOP -> shop();
        }
    }

    void continueJourney() {
        checkAct();
        if (actNumber != 4)
            randomEncounter();
    }

    void characterInfo() {
        console.clear();
        console.heading("ИНФОРМАЦИЯ О ГЕРОЕ");
        console.print(player.name + "\tHP: " + player.hp + "/" + player.maxHp);
        console.separator(20);
        console.print("LVL: " + player.lvl + "\tЗолото: " + player.gold);
        console.separator(20);
        console.print("XP: " + player.xp + "/" + player.maxXP);
        console.separator(20);
        console.print("№ Лекарств: " + player.pots);
        console.separator(20);

        if (player.numAtkUpgrades > 0) {
            console.print("Атака: " + player.atkUpgrades[player.numAtkUpgrades - 1]);
            console.separator(20);
        } else console.print("Атака: Атака 0");
        if (player.numDefUpgrades > 0) {
            console.print("Защита: " + player.defUpgrades[player.numDefUpgrades - 1]);
            console.separator(20);
        } else console.print("Защита: Защита 0");

        console.waitForContinue();
    }

    void shop() {
        console.clear();
        console.heading("Вы встретили загадочную персону. \nОн вам что-то предлагает: ");
        int price = (int) (Math.random() * (10 + player.pots * 3) + 10 + player.pots);
        console.print("-Лечебное зелье: " + price + " монет(ы)");
        console.separator(20);
        console.print("Не хочешь купить?\n(1) Да\n(2) Нет");
        int input = console.askInt("-> ", 2);
        if (input == 1) {
            console.clear();
            if (player.gold >= price) {
                console.heading("Вы купили лекарство за " + price + " монет(ы)");
                player.pots++;
                player.gold -= price;
            } else {
                console.heading("У вас недостаточно денег");
            }
            console.waitForContinue();
        }
    }

    void takeRest() {
        console.clear();
        if (player.hp < player.maxHp) {
            if (player.restsLeft >= 1) {
                console.heading("Вам попадается спокойное место, не хотите отдохнуть? (осталось " + player.restsLeft + " передышок)");
                console.print("(1) Да\n(2) Нет");
                int input = console.askInt("-> ", 2);
                if (input == 1) {
                    console.clear();
                    int hpRestored = (int) (Math.random() * (player.lvl + 10) + 8);
                    player.hp += hpRestored;
                    if (player.hp > player.maxHp)
                        player.hp = player.maxHp;
                    console.print("Вы отдохнули и востановили " + hpRestored + " HP");
                    console.print("Ваше HP теперь: " + player.hp + "/" + player.maxHp);
                    player.restsLeft--;
                } else {
                    console.clear();
                    console.heading("У вас нет времени на отдых!");
                }
            } else {
                console.heading("Вам попадается спокойное место, но у вас нет времени на отдых!");
            }
        } else {
            console.heading("Вам попадается спокойное место, но вы полностью здоровы. Идём дальше!");
        }
        console.waitForContinue();
    }

    void randomBattle() {
        console.clear();
        console.heading("Вы встретили врага!");
        console.waitForContinue();
        String[] enemyNames = currentAct.enemyNames();
        battle(new Enemy(enemyNames[(int) (Math.random() * enemyNames.length)], player.lvl));
    }

    void battle(Enemy enemy) {
        while (true) {
            console.clear();
            console.heading(enemy.name + "\nHP: " + enemy.hp + "/" + enemy.maxHp);
            console.heading(player.name + "\nHP: " + player.hp + "/" + player.maxHp);
            console.print("Выберите действие: ");
            console.separator(20);
            console.print("(1) Атака \n(2) Использовать лекарство \n(3) Пытаться убежать");
            int input = console.askInt("-> ", 3);
            if (input == 1) {
                int dmg = player.attack() - enemy.defend();
                int dmgTook = enemy.attack() - player.defend();
                if (dmgTook < 0) {
                    dmg -= dmgTook / 2;
                    dmgTook = 0;
                }
                if (dmg < 0)
                    dmg = 0;
                player.hp -= dmgTook;
                enemy.hp -= dmg;
                console.clear();
                console.heading("АТАКА");
                console.print("Вы нанесли " + dmg + " урона по " + enemy.name + ".");
                console.separator(15);
                console.print(enemy.name + " нанес вам урон: " + dmgTook);
                console.waitForContinue();
                if (player.hp <= 0) {
                    playerDied();
                } else if (enemy.hp <= 0) {
                    console.clear();
                    console.heading("Вы победили " + enemy.name + "!");
                    player.xp += enemy.xp;
                    console.print("Вы заработали " + enemy.xp + " XP!");
                    boolean addRest = (Math.random() * 5 + 1 <= 2.25);
                    int goldEarned = (int) (Math.random() * enemy.xp);
                    if (addRest) {
                        player.restsLeft++;
                        console.print("У вас появилось время отдохнуть");
                    }
                    if (goldEarned > 0) {
                        player.gold += goldEarned;
                        console.print("Вы нашли " + goldEarned + " золота.");
                    }
                    console.waitForContinue();
                    checkXP();
                    break;
                }
            } else if (input == 2) {
                console.clear();
                if (player.pots > 0 && player.hp < player.maxHp) {
                    console.heading("Вы хотите выпить лекарство? (" + player.pots + " осталось).");
                    console.print("(1) Да\n(2) Нет");
                    input = console.askInt("-> ", 2);
                    if (input == 1) {
                        player.hp = player.maxHp;
                        console.clear();
                        console.heading("Ваше здоровье востановлено");
                        console.waitForContinue();
                    }
                } else {
                    console.heading("У вас нет лекарств");
                    console.waitForContinue();
                }
            } else {
                console.clear();
                if (actNumber != 4) {
                    if (Math.random() * 10 + 1 <= 3.5) {
                        console.heading("Вы убежали от " + enemy.name);
                        console.waitForContinue();
                        break;
                    } else {
                        console.heading("Вы подскользнулись и не смогли убежать.");
                        int dmgTook = enemy.attack();
                        console.print("В спешке вы получили " + dmgTook + " урона от " + enemy.name + "!");
                        console.waitForContinue();
                        if (player.hp <= 0) {
                            playerDied();
                        }
                    }
                } else {
                    console.heading("ВЫ НЕ МОЖЕТЕ УБЕЖАТЬ ОТ БОССА!");
                    console.waitForContinue();
                }
            }
        }
    }

    void printMenu() {
        console.clear();
        console.heading(currentAct.place());
        console.print("Выберите действие: ");
        console.separator(20);
        console.print("(1) Продолжить приключение");
        console.print("(2) Информация о персонаже");
        console.print("(3) Выйти из игры");
    }

    void finalBattle() {
        battle(new Enemy("Падший Король", 9));
        Story.printEnd(console, player);
        running = false;
    }

    void playerDied() {
        console.clear();
        console.heading("ВЫ УМЕРЛИ");
        console.heading("Вы получили " + player.lvl + " уровень в вашем приключении.");
        System.exit(0);
    }

    void loop() {
        while (running) {
            printMenu();
            int input = console.askInt("->", 3);
            if (input == 1)
                continueJourney();
            else if (input == 2)
                characterInfo();
            else
                running = false;
        }
    }
}
