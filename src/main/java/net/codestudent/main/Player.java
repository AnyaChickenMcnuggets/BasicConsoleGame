package net.codestudent.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player extends Character {

    public int lvl, maxXP, gold, restsLeft;
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
        super(name, 20, 0);
        this.console = console;
        this.lvl = 1;
        this.gold = 15;
        this.maxXP = 20;
        this.restsLeft = 1;
        this.equippedWeapon = ItemCatalog.RUSTED_SHORTSWORD;
        this.equippedArmor = ItemCatalog.LEATHER_JERKIN;
        if (!blank)
            chooseSkill(0);
    }

    static Player blank(String name, Console console) {
        return new Player(name, console, true);
    }

    @Override
    public int armorClass() {
        int total = 10;
        if (equippedArmor != null)
            total += equippedArmor.armorClassBonus();
        for (SkillNode node : unlockedSkills)
            total += node.armorClassDelta();
        return total;
    }

    @Override
    public int attackBonus() {
        Weapon weapon = equippedWeapon != null ? equippedWeapon : ItemCatalog.UNARMED;
        int total = weapon.attackBonus();
        for (SkillNode node : unlockedSkills)
            total += node.attackBonusDelta();
        return total;
    }

    @Override
    public DiceExpr damageDice() {
        Weapon weapon = equippedWeapon != null ? equippedWeapon : ItemCatalog.UNARMED;
        int bonusDamage = 0;
        for (SkillNode node : unlockedSkills)
            bonusDamage += node.bonusDamageDelta();
        DiceExpr base = weapon.damageDice();
        return new DiceExpr(base.count(), base.sides(), base.modifier() + bonusDamage);
    }

    public void lvlUP() {
        console.clear();
        console.heading("Level Up!");
        console.print("Your wounds close and your strength grows. Full health restored.");
        console.waitForContinue();
        maxXP += 10;
        lvl += 1;
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
