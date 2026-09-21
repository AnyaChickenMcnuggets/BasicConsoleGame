package net.codestudent.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player extends Character {

    private static final int[] STANDARD_ARRAY = {15, 14, 13, 12, 10, 8};
    private static final List<String> ABILITY_NAMES =
        List.of("Strength", "Dexterity", "Constitution", "Intelligence", "Wisdom", "Charisma");
    private static final int PROFICIENCY_BONUS = 2;

    public int lvl, maxXP, gold, restsLeft;
    public int str, dex, con, intel, wis, cha;
    public SkillNode.Branch branch;
    public int skillTier = -1;
    public List<SkillNode> unlockedSkills = new ArrayList<>();

    public Weapon equippedWeapon;
    public Armor equippedArmor;
    public List<Item> inventory = new ArrayList<>();

    public Map<String, QuestProgress> questProgress = new HashMap<>();

    private final Console console;

    public Player(String name, Console console) {
        this(name, console, false);
    }

    private Player(String name, Console console, boolean blank) {
        super(name, 1, 0);
        this.console = console;
        this.lvl = 1;
        this.gold = 15;
        this.maxXP = 20;
        this.restsLeft = 1;
        this.equippedWeapon = ItemCatalog.RUSTED_SHORTSWORD;
        this.equippedArmor = ItemCatalog.LEATHER_JERKIN;
        if (!blank) {
            assignAbilityScores();
            chooseSkill(0);
        }
    }

    static Player blank(String name, Console console) {
        return new Player(name, console, true);
    }

    static int modifier(int score) {
        return Math.floorDiv(score - 10, 2);
    }

    private static String formatModifier(int mod) {
        return mod >= 0 ? "+" + mod : String.valueOf(mod);
    }

    @Override
    public int armorClass() {
        int total = 10 + modifier(dex);
        if (equippedArmor != null)
            total += equippedArmor.armorClassBonus();
        for (SkillNode node : unlockedSkills)
            total += node.armorClassDelta();
        return total;
    }

    @Override
    public int attackBonus() {
        Weapon weapon = equippedWeapon != null ? equippedWeapon : ItemCatalog.UNARMED;
        int total = modifier(str) + PROFICIENCY_BONUS + weapon.attackBonus();
        for (SkillNode node : unlockedSkills)
            total += node.attackBonusDelta();
        return total;
    }

    @Override
    public DiceExpr damageDice() {
        Weapon weapon = equippedWeapon != null ? equippedWeapon : ItemCatalog.UNARMED;
        int bonusDamage = modifier(str);
        for (SkillNode node : unlockedSkills)
            bonusDamage += node.bonusDamageDelta();
        DiceExpr base = weapon.damageDice();
        return new DiceExpr(base.count(), base.sides(), base.modifier() + bonusDamage);
    }

    private void assignAbilityScores() {
        console.clear();
        console.heading("Assign your ability scores");
        console.print("Standard array: 15, 14, 13, 12, 10, 8. Assign each value to an ability.");
        console.waitForContinue();

        List<String> remaining = new ArrayList<>(ABILITY_NAMES);
        Map<String, Integer> scores = new HashMap<>();
        for (int value : STANDARD_ARRAY) {
            if (remaining.size() == 1) {
                scores.put(remaining.remove(0), value);
                continue;
            }
            console.clear();
            console.heading("Assign " + value + " to which ability?");
            for (int i = 0; i < remaining.size(); i++)
                console.print("(" + (i + 1) + ") " + remaining.get(i));
            int input = console.askInt("-> ", remaining.size());
            scores.put(remaining.remove(input - 1), value);
        }

        str = scores.get("Strength");
        dex = scores.get("Dexterity");
        con = scores.get("Constitution");
        intel = scores.get("Intelligence");
        wis = scores.get("Wisdom");
        cha = scores.get("Charisma");

        maxHp = 12 + modifier(con);
        hp = maxHp;

        console.clear();
        console.heading("Your abilities");
        console.print("Strength: " + str + " (" + formatModifier(modifier(str)) + ")");
        console.print("Dexterity: " + dex + " (" + formatModifier(modifier(dex)) + ")");
        console.print("Constitution: " + con + " (" + formatModifier(modifier(con)) + ")");
        console.print("Intelligence: " + intel + " (" + formatModifier(modifier(intel)) + ")");
        console.print("Wisdom: " + wis + " (" + formatModifier(modifier(wis)) + ")");
        console.print("Charisma: " + cha + " (" + formatModifier(modifier(cha)) + ")");
        console.waitForContinue();
    }

    public void lvlUP() {
        int hpGain = 6 + modifier(con);
        console.clear();
        console.heading("Level Up!");
        console.print("Your wounds close and your strength grows. Max HP +" + hpGain + ". Full health restored.");
        console.waitForContinue();
        maxXP += 10;
        lvl += 1;
        maxHp += hpGain;
        hp = maxHp;
        if (skillTier < 3)
            chooseSkill(skillTier + 1);
    }

    private void chooseSkill(int tier) {
        List<SkillNode> options = SkillTree.choicesForTier(tier, branch);
        SkillNode chosen;
        console.clear();
        if (options.size() == 1) {
            chosen = options.get(0);
            console.heading("New talent: " + chosen.name());
            console.print(chosen.description());
        } else {
            console.heading(tier == 0 ? "Choose your path" : "Choose your talent");
            for (int i = 0; i < options.size(); i++) {
                SkillNode option = options.get(i);
                console.print("(" + (i + 1) + ") " + option.name() + " - " + option.description());
            }
            int input = console.askInt("-> ", options.size());
            chosen = options.get(input - 1);
        }
        unlockedSkills.add(chosen);
        skillTier = tier;
        if (branch == null)
            branch = chosen.branch();
        if (chosen.maxHpDelta() != 0) {
            maxHp += chosen.maxHpDelta();
            hp += chosen.maxHpDelta();
        }
        console.clear();
        console.heading("You have gained " + chosen.name() + "!");
        console.print(chosen.description());
        console.waitForContinue();
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public void equip(Weapon weapon) {
        inventory.remove(weapon);
        if (equippedWeapon != null)
            inventory.add(equippedWeapon);
        equippedWeapon = weapon;
    }

    public void equip(Armor armor) {
        inventory.remove(armor);
        if (equippedArmor != null)
            inventory.add(equippedArmor);
        equippedArmor = armor;
    }

    public void useConsumable(Consumable consumable) {
        hp = Math.min(maxHp, hp + consumable.healAmount());
        inventory.remove(consumable);
    }

    public QuestProgress questProgress(String questId) {
        return questProgress.computeIfAbsent(questId, id -> new QuestProgress());
    }
}
