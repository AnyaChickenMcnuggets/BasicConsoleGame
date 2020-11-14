package net.codestudent.main;
import java.util.Scanner;

public class GameLogic {
    static Scanner scanner = new Scanner(System.in);

    static Player player;

    public static boolean isRunning;

    //случайные события
    public static String[] encounters = {"Сражение", "Сражение", "Сражение", "Отдых", "Отдых"};

    //имена врагов
    public static String[] enemies = {"Огр", "Огр", "Гоблин", "Гоблин", "Агрессивный Камень"};

    //Элементы истории
    public static int place =0, act = 1;
    public static String[] places = {"Бесконечные Горы", "Призрачная Низина", "Кровавый Перевал", "Обитель Короля"};

    // Метод чтобы получить ответ игрока
    public static int readInt(String prompt, int userChoices){
        int input;

        do {
            System.out.println(prompt);
            try {
                input = Integer.parseInt(scanner.next());
            }catch (Exception e){
                input = -1;
                System.out.println("Пожалуйста введите число!");
            }
        }while (input < 1 || input > userChoices);
        return input;
    }

    //Метод для симуляции очистки консоли
    public static void clearConsole(){
        for (int i = 0; i < 100; i++)
            System.out.println( );
    }

    //Метод для сепаратора с длиной n
    public static void printSeparator(int n){
        for(int i = 0; i < n; i++)
            System.out.print("-");
        System.out.println();
    }

    //Метод для заголовка
    public static void printHeading(String title){
        printSeparator(30);
        System.out.println(title);
        printSeparator(30);
    }

    //Метод для паузы пока пользователь не напишет что-нибудь
    public static void anythingToContinue(){
        System.out.println("\nВведите что-нибудь чтобы продолжить...");
        scanner.next();
    }

    //Метод для начала игры
    public static void startGame(){
        boolean nameSet = false;
        String name;
        //Вывод Заглавного экрана
        clearConsole();
        printSeparator(40);
        printSeparator(30);
        System.out.println("Последний Король");
        printSeparator(30);
        printSeparator(40);
        anythingToContinue();

        //Получение имени Героя
        do {
            clearConsole();
            printHeading("Как вас зовут?");
            name = scanner.next();
            //Уточнение имени
            clearConsole();
            printHeading("Вас зовут " + name + ".\nЭто правильно?");
            System.out.println("(1) Да!");
            System.out.println("(2) Нет, Я хочу изменить своё имя.");
            int input = readInt("-> ", 2);
            if (input == 1)
                nameSet = true;
        }while (!nameSet);

        //Интро Истории
        Story.printIntro();

        //Создание Героя с именем
        player = new Player(name);

        //Интро первого акта истории
        Story.printFirstActIntro();

        //Установка isRunning= true чтобы игра шла
        isRunning = true;

        //Начало основного процесса игры
        gameLoop();
    }

    //Проверка опыта
    public static void checkXP(){
        if (player.xp >= player.maxXP){
            player.xp -= player.maxXP;
            player.lvlUP();
        }
    }

    //Смена локаций в зависимости от xp Героя
    public static void checkAct(){
        //Смена акта в зависимости от xp
        if (player.lvl >= 2 && act == 1){
            //Применение акта и локации
            act = 2;
            place = 1;
            //Конец истории
            Story.printFirstActOutro();
            //Поднятие уровня
            //player.chooseTrait();
            //Продолжение истории
            Story.printSecondActIntro();
            //Смена имен врагов
            enemies[0] = "Призрак";
            enemies[1] = "Призрак";
            enemies[2] = "Воришка";
            enemies[3] = "Стая Волков";
            enemies[4] = "Безглавый Рыцарь";
            //Смена случайных событий
            encounters[0] = "Сражение";
            encounters[1] = "Сражение";
            encounters[2] = "Сражение";
            encounters[3] = "Отдых";
            encounters[4] = "Подозрительный Странник";
            //Полное лечений Героя
            //player.hp = player.maxHp;
        }else if (player.lvl >=3 && act == 2){
            //Применение акта и локации
            act = 3;
            place = 2;
            //Конец истории
            Story.printSecondActOutro();
            //Поднятие уровня
            //player.chooseTrait();
            //Продолжение истории
            Story.printThirdActIntro();
            //Смена имен врагов
            enemies[0] = "Бульник";
            enemies[1] = "Стрыга";
            enemies[2] = "Украинский Борщ";
            enemies[3] = "Верховный Вампир";
            enemies[4] = "Кровавая Жижа";
            //Смена случайных событий
            encounters[0] = "Сражение";
            encounters[1] = "Сражение";
            encounters[2] = "Сражение";
            encounters[3] = "Отдых";
            encounters[4] = "Подозрительный Гоблин";
            //Полное лечений Героя
            //player.hp = player.maxHp;
        }else if (player.lvl >=4     && act == 3){
            //Применение акта и локации
            act = 4;
            place = 3;
            //Конец истории
            Story.printThirdActOutro();
            //Поднятие уровня
            //player.chooseTrait();
            //Продолжение истории
            Story.printFourthActIntro();
            //Полное лечений Героя
            //player.hp = player.maxHp;
            //Вызов финального босса
            finalBattle();
        }

    }

    //Метод для выщитывания случайных событий
    public static void randomEncounter(){
        //Случайное число между 0 и кол-вом случайных событий
        int encounter = (int) (Math.random()* encounters.length);
        //Вызов методов событий
        if (encounters[encounter].equals("Сражение")) {
            randomBattle();
        }else if (encounters[encounter].equals("Отдых")) {
            takeRest();
        }else {
            shop();
        }
    }

    //Метод для продолжения приключения
    public static void continueJourney() {
        //Проверка xp Героя для перехода на след. АКТ
        checkAct();
        //Проверка что не последний акт
        if (act != 4)
            randomEncounter();

    }

    //Выведение информации о персонаже
    public static void characterInfo(){
        clearConsole();
        printHeading("ИНФОРМАЦИЯ О ГЕРОЕ");
        System.out.println(player.name + "\tHP: " + player.hp + "/" + player.maxHp);
        printSeparator(20);
        //Уровень и золото
        System.out.println("LVL: " + player.lvl + "\tЗолото: " + player.gold);
        printSeparator(20);
        //Опыт
        System.out.println("XP: " + player.xp + "/" + player.maxXP);
        printSeparator(20);
        //Кол-во Лекарств
        System.out.println("№ Лекарств: " + player.pots);
        printSeparator(20);

        //Уровень улучшений
        if (player.numAtkUpgrades > 0){
            System.out.println("Атака: " + player.atkUpgrades[player.numAtkUpgrades - 1]);
            printSeparator(20);
        }else System.out.println("Атака: Атака 0");
        if (player.numDefUpgrades > 0){
            System.out.println("Защита: " + player.defUpgrades[player.numDefUpgrades - 1]);
            printSeparator(20);
        }else System.out.println("Защита: Защита 0");

        anythingToContinue();
    }

    //Событие с магазином
    public static void shop(){
        clearConsole();
        printHeading("Вы встретили загадочную персону. \nОн вам что-то предлагает: ");
        int price = (int) (Math.random()* (10 + player.pots*3) + 10 + player.pots);
        System.out.println("-Лечебное зелье: " + price + " монет(ы)");
        printSeparator(20);
        //Просьба купить одно
        System.out.println("Не хочешь купить?\n(1) Да\n(2) Нет");
        int input = readInt("-> ", 2);
        //Проверка хочет ли игрок купить что-то
        if (input == 1){
            clearConsole();
            //Проверка золота
            if (player.gold >= price){
                printHeading("Вы купили лекарство за " + price + " монет(ы)");
                player.pots++;
                player.gold -= price;
            }else {
                printHeading("У вас недостаточно денег");
            }
            anythingToContinue();
        }
    }

    //Отдых
    public static void takeRest(){
        clearConsole();
        if (player.hp < player.maxHp){
            if (player.restsLeft >= 1){
                printHeading("Вам попадается спокойное место, не хотите отдохнуть? (осталось " + player.restsLeft + " передышок)");
                System.out.println("(1) Да\n(2) Нет");
                int input = readInt("-> ", 2);
                if (input == 1){
                    //Игрок соглашается на отдых
                    clearConsole();
                    int hpRestored = (int) (Math.random()*(player.lvl + 10) + 8);
                    player.hp += hpRestored;
                    if (player.hp > player.maxHp)
                        player.hp = player.maxHp;
                    System.out.println("Вы отдохнули и востановили " + hpRestored + " HP");
                    System.out.println("Ваше HP теперь: " + player.hp + "/" + player.maxHp);
                    player.restsLeft--;
                }else {
                    clearConsole();
                    printHeading("У вас нет времени на отдых!");
                }
            }else {
                printHeading("Вам попадается спокойное место, но у вас нет времени на отдых!");
            }
        }else {
            printHeading("Вам попадается спокойное место, но вы полностью здоровы. Идём дальше!");
        }
        anythingToContinue();
    }

    //Создание случайного сражения
    public static void randomBattle(){
        clearConsole();
        printHeading("Вы встретили врага!");
        anythingToContinue();
        //Создание врага со случайным именем
        battle(new Enemy(enemies[(int)(Math.random()*enemies.length)], player.lvl));
    }

    //Основной метод для СРАЖЕНИЯ
    public static void battle(Enemy enemy){
        //основной цикл для сражения
        while (true){
            clearConsole();
            printHeading(enemy.name + "\nHP: " + enemy.hp + "/" + enemy.maxHp);
            printHeading(player.name + "\nHP: " + player.hp + "/" + player.maxHp);
            System.out.println("Выберите действие: ");
            printSeparator(20);
            System.out.println("(1) Атака \n(2) Использовать лекарство \n(3) Пытаться убежать");
            int input = readInt("-> ", 3);
            //ответная реакция на ввод игрока
            if (input == 1){
                //Драка
                //Вычисление урона по врагу и по герою
                int dmg = player.attack() - enemy.defend();
                int dmgTook = enemy.attack() - player.defend();
                //проверка на негативный урон
                if (dmgTook < 0){
                    //добавление ответного урона игроку, если его защита слишком высокая
                    dmg -= dmgTook/2;
                    dmgTook = 0;
                }
                if (dmg < 0)
                    dmg = 0;
                //Применение урона
                player.hp -= dmgTook;
                enemy.hp -= dmg;
                //Выведение информации о раунде
                clearConsole();
                printHeading("АТАКА");
                System.out.println("Вы нанесли " + dmg + " урона по " + enemy.name + ".");
                printSeparator(15);
                System.out.println(enemy.name + " нанес вам урон: " + dmgTook);
                anythingToContinue();
                //Проверка мертв ли герой
                if (player.hp <= 0){
                    playerDied(); //Конец игры
                }else if (enemy.hp <= 0){
                    //Враг повержен
                    clearConsole();
                    printHeading("Вы победили " + enemy.name + "!");
                    //Увеличение опыта
                    player.xp += enemy.xp;
                    System.out.println("Вы заработали " + enemy.xp + " XP!");
                    //Случайные вещи с врагов
                    boolean addRest = (Math.random()*5 + 1 <=2.25);
                    int goldEarned = (int) (Math.random()*enemy.xp);
                    if (addRest){
                        player.restsLeft++;
                        System.out.println("У вас появилось время отдохнуть");
                    }
                    if (goldEarned > 0){
                        player.gold += goldEarned;
                        System.out.println("Вы нашли " + goldEarned + " золота.");
                    }
                    anythingToContinue();
                    checkXP();
                    break;
                }
            }else if (input == 2){
                //Использование лекарства
                clearConsole();
                if (player.pots > 0 && player.hp < player.maxHp){
                    //Игрок может использовать лекарство
                    //Контрольный вопрос
                    printHeading("Вы хотите выпить лекарство? (" + player.pots + " осталось).");
                    System.out.println("(1) Да\n(2) Нет");
                    input = readInt("-> ", 2);
                    if (input == 1){
                        //Игрок использует лекарство
                        player.hp = player.maxHp;
                        clearConsole();
                        printHeading("Ваше здоровье востановлено");
                        anythingToContinue();
                    }
                }else {
                    //Игрок не может использовать лекарства
                    printHeading("У вас нет лекарств");
                    anythingToContinue();
                }

            }else {
                //Попытка убежать
                clearConsole();
                //35% шанс на побег
               if (act != 4){
                   if (Math.random()*10 + 1 <= 3.5){
                       printHeading("Вы убежали от " + enemy.name);
                       anythingToContinue();
                       break;
                   }else {
                       printHeading("Вы подскользнулись и не смогли убежать.");
                       //вычисление полученного урона
                       int dmgTook = enemy.attack();
                       System.out.println("В спешке вы получили " + dmgTook + " урона от " + enemy.name + "!");
                       anythingToContinue();
                       //Проверка мертв ли герой
                       if (player.hp <= 0){
                           playerDied(); //Конец игры
                       }
                   }
               }else{
                   printHeading("ВЫ НЕ МОЖЕТЕ УБЕЖАТЬ ОТ БОССА!");
                   anythingToContinue();
               }
            }
        }
    }

    //Главное меню
    public static void printMenu(){
        clearConsole();
        printHeading(places[place]);
        System.out.println("Выберите действие: ");
        printSeparator(20);
        System.out.println("(1) Продолжить приключение");
        System.out.println("(2) Информация о персонаже");
        System.out.println("(3) Выйти из игры");
    }

    //Последний бой
    public static void finalBattle(){
        //Создание Последнего Босса
        battle(new Enemy("Падший Король", 9));
        //История. Конец
        Story.printEnd(player);
        isRunning = false;
    }

    //Метод когда герой мертв
    public static void playerDied(){
        clearConsole();
        printHeading("ВЫ УМЕРЛИ");
        printHeading("Вы получили " + player.lvl + " уровень в вашем приключении.");
        System.exit(0);
    }

    //Основной цикл игры
    public static void gameLoop(){
        while (isRunning){
            printMenu();
            int input = readInt("->", 3);
            if (input == 1)
                continueJourney();
            else if (input == 2)
                characterInfo();
            else
                isRunning = false;
        }
    }

}
