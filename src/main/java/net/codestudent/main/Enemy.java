package net.codestudent.main;

public class Enemy extends Character {

    public final EnemyTemplate template;

    public Enemy(EnemyTemplate template, Dice dice) {
        super(template.name(), dice.roll(template.hitDice()), template.xpReward());
        this.template = template;
    }

    @Override
    public int armorClass() {
        return template.armorClass();
    }

    @Override
    public int attackBonus() {
        return template.attackBonus();
    }

    @Override
    public DiceExpr damageDice() {
        return template.damageDice();
    }
}
