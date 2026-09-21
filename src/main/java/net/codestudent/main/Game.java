package net.codestudent.main;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

    private final Console console;
    private final Dice dice;
    private final Path savePath;
    private final Region region = Region.GREYWOOD_MARCHES;

    Player player;
    boolean running;

    public Game(Console console) {
        this(console, new Dice(new Random()));
    }

    Game(Console console, Dice dice) {
        this(console, dice, Path.of("saves", "save.properties"));
    }

    Game(Console console, Dice dice, Path savePath) {
        this.console = console;
        this.dice = dice;
        this.savePath = savePath;
    }

    public void start() {
        console.clear();
        console.separator(40);
        console.separator(30);
        console.print("THE HOLLOW CROWN");
        console.separator(30);
        console.separator(40);
        console.waitForContinue();

        if (SaveManager.exists(savePath)) {
            console.clear();
            console.heading("A journey already in progress");
            console.print("(1) Continue your journey");
            console.print("(2) Begin a new journey");
            int input = console.askInt("-> ", 2);
            if (input == 1) {
                player = SaveManager.load(savePath, console);
                running = true;
                loop();
                return;
            }
        }

        newGame();
        running = true;
        loop();
    }

    private void newGame() {
        boolean nameSet = false;
        String name;
        do {
            console.clear();
            console.heading("What is your name, traveler?");
            name = console.askLine();
            console.clear();
            console.heading("Your name is " + name + ".\nIs that correct?");
            console.print("(1) Yes!");
            console.print("(2) No, let me change it.");
            int input = console.askInt("-> ", 2);
            if (input == 1)
                nameSet = true;
        } while (!nameSet);

        Story.play(console, Story.INTRO);
        player = new Player(name, console);
        Story.play(console, Story.REGION_INTRO);
    }

    private void loop() {
        while (running) {
            printMenu();
            int input = console.askInt("-> ", 5);
            switch (input) {
                case 1 -> ventureIntoMarsh();
                case 2 -> visitMillhaven();
                case 3 -> characterInfo();
                case 4 -> saveGame();
                default -> running = false;
            }
        }
    }

    private void printMenu() {
        console.clear();
        console.heading(region.name());
        console.print("What will you do?");
        console.separator(20);
        console.print("(1) Venture into the marsh");
        console.print("(2) Visit Millhaven");
        console.print("(3) Character info");
        console.print("(4) Save your journey");
        console.print("(5) Quit");
    }

    private void ventureIntoMarsh() {
        Encounter[] table = region.encounterTable();
        Encounter encounter = table[dice.roll(table.length) - 1];
        switch (encounter) {
            case BATTLE -> randomBattle();
            case REST -> takeRest();
            case SHOP -> wanderingMerchant();
        }
    }

    private void randomBattle() {
        List<EnemyTemplate> pool = region.wildernessEnemies();
        EnemyTemplate template = pool.get(dice.roll(pool.size()) - 1);
        console.clear();
        console.heading("A foe blocks your path!");
        console.print(template.description());
        console.waitForContinue();
        battle(new Enemy(template, dice), true);
    }

    private void wanderingMerchant() {
        console.clear();
        console.heading("A hooded stranger blocks the path.");
        console.print("\"Potions, traveler? Fair price, I promise.\"");
        int price = 10 + dice.roll(15);
        console.separator(20);
        console.print("- Minor Healing Potion: " + price + " gold");
        console.print("(1) Buy  (2) Walk on");
        int input = console.askInt("-> ", 2);
        if (input == 1) {
            console.clear();
            if (player.gold >= price) {
                player.gold -= price;
                player.addItem(ItemCatalog.MINOR_HEALING_POTION);
                console.heading("You buy a Minor Healing Potion.");
            } else {
                console.heading("You don't have enough gold.");
            }
            console.waitForContinue();
        }
    }

    private void takeRest() {
        console.clear();
        if (player.hp < player.maxHp) {
            if (player.restsLeft >= 1) {
                console.heading("You find a sheltered, quiet spot. Rest here? (" + player.restsLeft + " chances left)");
                console.print("(1) Yes  (2) No");
                int input = console.askInt("-> ", 2);
                if (input == 1) {
                    console.clear();
                    int hpRestored = dice.roll(DiceExpr.of(1, 8, player.lvl + 2));
                    player.hp = Math.min(player.maxHp, player.hp + hpRestored);
                    console.print("You rest and recover " + hpRestored + " HP.");
                    console.print("Your HP is now " + player.hp + "/" + player.maxHp + ".");
                    player.restsLeft--;
                } else {
                    console.clear();
                    console.heading("No time to rest now.");
                }
            } else {
                console.heading("You find a quiet spot, but you're too weary to make use of it.");
            }
        } else {
            console.heading("You find a quiet spot, but you're already at full health. Onward.");
        }
        console.waitForContinue();
    }

    private void battle(Enemy enemy, boolean canFlee) {
        while (true) {
            console.clear();
            console.heading(enemy.name + "\nHP: " + Math.max(enemy.hp, 0) + "/" + enemy.maxHp);
            console.heading(player.name + "\nHP: " + Math.max(player.hp, 0) + "/" + player.maxHp);
            console.print("What will you do?");
            console.separator(20);
            int choices = canFlee ? 3 : 2;
            console.print("(1) Attack" + "\n(2) Use an item" + (canFlee ? "\n(3) Try to flee" : ""));
            int input = console.askInt("-> ", choices);

            if (input == 1) {
                AttackResult playerAttack = Combat.resolveAttack(player, enemy, dice);
                console.clear();
                console.heading("Your attack");
                if (playerAttack.hit()) {
                    enemy.hp -= playerAttack.damage();
                    console.print((playerAttack.critical() ? "A critical hit! " : "") +
                        "You strike " + enemy.name + " for " + playerAttack.damage() + " damage.");
                } else {
                    console.print("Your attack misses.");
                }
                console.waitForContinue();

                if (enemy.hp <= 0) {
                    winBattle(enemy);
                    break;
                }

                AttackResult enemyAttack = Combat.resolveAttack(enemy, player, dice);
                console.clear();
                console.heading(enemy.name + "'s attack");
                if (enemyAttack.hit()) {
                    player.hp -= enemyAttack.damage();
                    console.print((enemyAttack.critical() ? "A critical hit! " : "") +
                        enemy.name + " hits you for " + enemyAttack.damage() + " damage.");
                } else {
                    console.print(enemy.name + "'s attack misses.");
                }
                console.waitForContinue();

                if (player.hp <= 0)
                    playerDied();

            } else if (input == 2) {
                useItemInBattle();
            } else {
                console.clear();
                if (dice.roll(100) <= 35) {
                    console.heading("You slip away from " + enemy.name + ".");
                    console.waitForContinue();
                    break;
                } else {
                    console.heading("You stumble and fail to escape.");
                    AttackResult enemyAttack = Combat.resolveAttack(enemy, player, dice);
                    if (enemyAttack.hit()) {
                        player.hp -= enemyAttack.damage();
                        console.print(enemy.name + " catches you for " + enemyAttack.damage() + " damage!");
                    } else {
                        console.print(enemy.name + "'s attack misses.");
                    }
                    console.waitForContinue();
                    if (player.hp <= 0)
                        playerDied();
                }
            }
        }
    }

    private void useItemInBattle() {
        console.clear();
        List<Consumable> potions = new ArrayList<>();
        for (Item item : player.inventory)
            if (item instanceof Consumable consumable)
                potions.add(consumable);

        if (potions.isEmpty()) {
            console.heading("You have no items to use.");
            console.waitForContinue();
            return;
        }

        console.heading("Use which item?");
        for (int i = 0; i < potions.size(); i++)
            console.print("(" + (i + 1) + ") " + potions.get(i).name() + " - " + potions.get(i).description());
        int input = console.askInt("-> ", potions.size());
        Consumable chosen = potions.get(input - 1);
        player.useConsumable(chosen);
        console.clear();
        console.heading("You use the " + chosen.name() + ".");
        console.print("You recover " + chosen.healAmount() + " HP.");
        console.waitForContinue();
    }

    private void winBattle(Enemy enemy) {
        console.clear();
        console.heading("You defeated " + enemy.name + "!");
        player.xp += enemy.xp;
        console.print("You gain " + enemy.xp + " XP!");
        int gold = dice.roll(enemy.template.goldDice());
        if (gold > 0) {
            player.gold += gold;
            console.print("You find " + gold + " gold.");
        }
        if (dice.roll(4) == 1) {
            player.restsLeft++;
            console.print("You find a moment to catch your breath.");
        }
        for (Item item : rollLoot(enemy.template)) {
            player.addItem(item);
            console.print("You find " + item.name() + ".");
        }
        console.waitForContinue();
        registerKill(enemy.template.id());
        checkLevelUp();
    }

    List<Item> rollLoot(EnemyTemplate template) {
        List<Item> drops = new ArrayList<>();
        for (LootEntry entry : template.lootTable())
            if (dice.roll(100) <= entry.percentChance())
                drops.add(entry.item());
        return drops;
    }

    void registerKill(String enemyTemplateId) {
        Quest quest = QuestBook.WOLVES_AT_THE_DOOR;
        if (!quest.targetEnemyId().equals(enemyTemplateId))
            return;
        QuestProgress progress = player.questProgress(quest.id());
        if (progress.state == QuestProgress.State.ACTIVE) {
            progress.killCount++;
            if (progress.killCount >= quest.requiredCount())
                progress.state = QuestProgress.State.READY_TO_TURN_IN;
        }
    }

    void checkLevelUp() {
        if (player.xp >= player.maxXP) {
            player.xp -= player.maxXP;
            player.lvlUP();
        }
    }

    private void characterInfo() {
        console.clear();
        console.heading("CHARACTER INFO");
        console.print(player.name + "\tHP: " + player.hp + "/" + player.maxHp);
        console.separator(20);
        console.print("Level: " + player.lvl + "\tGold: " + player.gold);
        console.separator(20);
        console.print("XP: " + player.xp + "/" + player.maxXP);
        console.separator(20);
        console.print("Armor Class: " + player.armorClass() + "\tAttack Bonus: +" + player.attackBonus());
        console.separator(20);
        console.print("STR " + player.str + "  DEX " + player.dex + "  CON " + player.con +
            "  INT " + player.intel + "  WIS " + player.wis + "  CHA " + player.cha);
        console.separator(20);
        console.print("Weapon: " + (player.equippedWeapon != null ? player.equippedWeapon.name() : "none"));
        console.print("Armor: " + (player.equippedArmor != null ? player.equippedArmor.name() : "none"));
        console.separator(20);
        console.print("Talents:");
        for (SkillNode node : player.unlockedSkills)
            console.print(" - " + node.name());
        console.separator(20);
        if (player.inventory.isEmpty()) {
            console.print("Inventory: empty");
        } else {
            console.print("Inventory:");
            for (Item item : player.inventory)
                console.print(" - " + item.name());
        }
        console.waitForContinue();
    }

    private void visitMillhaven() {
        Hub hub = region.hub();
        boolean visiting = true;
        while (visiting) {
            console.clear();
            console.heading(hub.name());
            console.print(hub.description());
            console.separator(20);
            List<Npc> npcs = hub.npcs();
            for (int i = 0; i < npcs.size(); i++)
                console.print("(" + (i + 1) + ") Talk to " + npcs.get(i).name());
            int shopOption = npcs.size() + 1;
            int leaveOption = npcs.size() + 2;
            console.print("(" + shopOption + ") Visit the shop");
            console.print("(" + leaveOption + ") Leave Millhaven");
            int input = console.askInt("-> ", leaveOption);
            if (input == leaveOption) {
                visiting = false;
            } else if (input == shopOption) {
                shopMenu(hub);
            } else {
                if (talkTo(npcs.get(input - 1)))
                    return; // climax was triggered mid-conversation
            }
        }
    }

    boolean talkTo(Npc npc) {
        console.clear();
        console.heading(npc.name());
        console.print(npc.description());
        console.print("");
        for (String line : npc.dialogue())
            console.print(line);

        if (npc.quest() != null) {
            Quest quest = npc.quest();
            QuestProgress progress = player.questProgress(quest.id());
            console.print("");
            switch (progress.state) {
                case NOT_STARTED -> {
                    console.heading(quest.title());
                    console.print(quest.description());
                    console.print("(1) Accept  (2) Not now");
                    if (console.askInt("-> ", 2) == 1) {
                        progress.state = QuestProgress.State.ACTIVE;
                        console.print("Quest accepted: " + quest.title());
                    }
                }
                case ACTIVE -> console.print("\"" + Bestiary.MARSH_WOLF.name() + "s slain: " +
                    progress.killCount + "/" + quest.requiredCount() + ".\"");
                case READY_TO_TURN_IN -> {
                    console.print("\"You've done it. Millhaven owes you a debt.\"");
                    player.gold += quest.rewardGold();
                    player.xp += quest.rewardXp();
                    String rewardLine = quest.rewardGold() + " gold, " + quest.rewardXp() + " XP";
                    if (quest.rewardItemId() != null) {
                        Item rewardItem = ItemCatalog.byId(quest.rewardItemId());
                        player.addItem(rewardItem);
                        rewardLine += ", " + rewardItem.name();
                    }
                    progress.state = QuestProgress.State.COMPLETE;
                    console.print("Reward: " + rewardLine + ".");
                    console.waitForContinue();
                    checkLevelUp();
                    return false;
                }
                case COMPLETE -> {
                    console.print("\"Rest well. You've more than earned it.\"");
                    if (player.lvl >= 3) {
                        console.print("");
                        console.print("\"The path to the Splintered Peaks lies open now, if you're ready for it.\"");
                        console.print("(1) Go to the Splintered Peaks  (2) Not yet");
                        if (console.askInt("-> ", 2) == 1) {
                            climax();
                            return true;
                        }
                    }
                }
            }
        }
        console.waitForContinue();
        return false;
    }

    private void shopMenu(Hub hub) {
        boolean shopping = true;
        while (shopping) {
            console.clear();
            console.heading("Millhaven Market");
            console.print("Gold: " + player.gold);
            console.separator(20);
            List<ShopEntry> entries = hub.shop();
            for (int i = 0; i < entries.size(); i++) {
                ShopEntry entry = entries.get(i);
                console.print("(" + (i + 1) + ") " + entry.item().name() + " - " + entry.price() + " gold");
                console.print("      " + entry.item().description());
            }
            int sellOption = entries.size() + 1;
            int leaveOption = entries.size() + 2;
            console.print("(" + sellOption + ") Sell an item");
            console.print("(" + leaveOption + ") Leave");
            int input = console.askInt("-> ", leaveOption);
            if (input == leaveOption) {
                shopping = false;
            } else if (input == sellOption) {
                sellMenu();
            } else {
                ShopEntry entry = entries.get(input - 1);
                console.clear();
                if (player.gold >= entry.price()) {
                    player.gold -= entry.price();
                    player.addItem(entry.item());
                    console.heading("You buy the " + entry.item().name() + ".");
                    if (entry.item() instanceof Weapon weapon) {
                        console.print("(1) Equip it now  (2) Keep it in your pack");
                        if (console.askInt("-> ", 2) == 1)
                            player.equip(weapon);
                    } else if (entry.item() instanceof Armor armor) {
                        console.print("(1) Equip it now  (2) Keep it in your pack");
                        if (console.askInt("-> ", 2) == 1)
                            player.equip(armor);
                    }
                } else {
                    console.heading("You don't have enough gold.");
                }
                console.waitForContinue();
            }
        }
    }

    private void sellMenu() {
        if (player.inventory.isEmpty()) {
            console.clear();
            console.heading("You have nothing to sell.");
            console.waitForContinue();
            return;
        }
        console.clear();
        console.heading("Sell which item?");
        console.print("Gold: " + player.gold);
        console.separator(20);
        List<Item> items = player.inventory;
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            console.print("(" + (i + 1) + ") " + item.name() + " - sells for " + sellPriceOf(item) + " gold");
        }
        int cancelOption = items.size() + 1;
        console.print("(" + cancelOption + ") Cancel");
        int input = console.askInt("-> ", cancelOption);
        if (input == cancelOption)
            return;
        Item chosen = items.get(input - 1);
        int sellPrice = sellPriceOf(chosen);
        player.inventory.remove(chosen);
        player.gold += sellPrice;
        console.clear();
        console.heading("You sell the " + chosen.name() + " for " + sellPrice + " gold.");
        console.waitForContinue();
    }

    private int sellPriceOf(Item item) {
        return (int) Math.ceil(item.value() * 0.5);
    }

    private void climax() {
        Story.play(console, Story.CLIMAX_INTRO);
        battle(new Enemy(Bestiary.THE_DROWNED_KNIGHT, dice), false);
        player.addItem(ItemCatalog.WARHAMMER_OF_THE_MARCHES);
        Story.play(console, Story.ENDING);
        running = false;
    }

    private void saveGame() {
        SaveManager.save(savePath, player);
        console.clear();
        console.heading("Your journey has been saved.");
        console.waitForContinue();
    }

    private void playerDied() {
        Story.play(console, Story.DEATH);
        console.clear();
        console.heading("You reached level " + player.lvl + " before the end.");
        System.exit(0);
    }
}
